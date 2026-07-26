# HotelBooking — Tài liệu kiến trúc & luồng hoạt động

Tài liệu mô tả kiến trúc tổng thể, các luồng nghiệp vụ chính và logic phân quyền của hệ thống đặt phòng khách sạn.

---

## 1. Tổng quan

- **Stack**: Spring Boot (Java 17), Spring Data JPA, Spring Security, Redis, PostgreSQL (H2 cho test), springdoc-openapi (Swagger).
- **Kiến trúc phân lớp**: `Controller → Service → Repository → Entity`.
  - **Controller**: nhận HTTP request, validate DTO (`@Valid`), gọi service, trả `ResponseEntity`.
  - **Service**: chứa logic nghiệp vụ + kiểm tra phân quyền chi tiết theo chủ sở hữu; đánh dấu `@Transactional`.
  - **Repository**: Spring Data JPA, truy vấn dữ liệu.
  - **Entity**: ánh xạ bảng, kế thừa `BaseEntity` (id + audit timestamps).
- **DTO**: request/response tách biệt entity. Không bao giờ trả entity trực tiếp ra ngoài (tránh lộ quan hệ lazy, vòng lặp JSON).

### Cấu trúc package

```
com.example.hotelbooking
├── config          # Security, JPA auditing, Redis, OpenAPI, seed data
├── controller      # REST endpoints
├── exception       # Exception tùy biến + GlobalExceptionHandler
├── model
│   ├── base        # BaseEntity (id, createdAt, updatedAt)
│   ├── dto/request # DTO đầu vào (có validation)
│   ├── dto/response# DTO đầu ra
│   ├── entity      # JPA entities
│   └── enums       # Các enum trạng thái/loại
├── repository      # Spring Data JPA repositories
└── service         # Business logic (+ impl cho Auth/Jwt)
```

---

## 2. Mô hình dữ liệu

### 2.1. Sơ đồ quan hệ thực thể (ERD)

Sơ đồ Mermaid (GitHub / IntelliJ render trực tiếp):

```mermaid
erDiagram
    USER ||--o{ BOOKING : "đặt"
    USER ||--o{ REVIEW : "viết"
    HOTEL ||--|| ADDRESS : "có"
    HOTEL ||--o{ ROOM : "chứa"
    HOTEL ||--o{ REVIEW : "được đánh giá"
    ROOM_TYPE ||--o{ ROOM : "phân loại"
    ROOM ||--o{ BOOKING : "được đặt"
    ROOM ||--o{ ROOM_AMENITY : ""
    AMENITY ||--o{ ROOM_AMENITY : ""
    BOOKING ||--o| PAYMENT : "thanh toán"
    BOOKING ||--o| REVIEW : "gắn (tùy chọn)"

    USER {
        Long id PK
        String email UK
        String password "BCrypt hash"
        String firstName
        String lastName
        String phoneNumber
        UserRole role "CUSTOMER|HOTEL_MANAGER|ADMIN"
        Boolean enabled
    }
    ADDRESS {
        Long id PK
        String streetAddress
        String city
        String state
    }
    HOTEL {
        Long id PK
        String name
        String description
        Integer starRating "1..5"
        String email
        String phoneNumber
        LocalTime checkInTime "mặc định 14:00"
        LocalTime checkOutTime "mặc định 12:00"
        Boolean enabled
        Long address_id FK
    }
    ROOM_TYPE {
        Long id PK
        String name
        String description
        Integer maxOccupancy
        String bedType
        Integer numberOfBeds
        Double sizeSquareMeters
        BigDecimal basePrice
    }
    ROOM {
        Long id PK
        String roomNumber "unique trong 1 hotel"
        RoomStatus status "AVAILABLE|OCCUPIED|MAINTENANCE|OUT_OF_SERVICE"
        BigDecimal pricePerNight
        String description
        Long hotel_id FK
        Long room_type_id FK
    }
    AMENITY {
        Long id PK
        String name UK
        String description
        String iconName
        AmenityType type "HOTEL_LEVEL|ROOM_LEVEL|BOTH"
    }
    ROOM_AMENITY {
        Long room_id PK_FK
        Long amenity_id PK_FK
    }
    BOOKING {
        Long id PK
        String bookingReference UK "BK-XXXXXXXX"
        LocalDate checkInDate
        LocalDate checkOutDate
        Integer numberOfGuests
        Integer numberOfNights "tự tính"
        BigDecimal totalAmount "pricePerNight * số đêm"
        String specialRequests
        BookingStatus status
        Long customer_id FK
        Long room_id FK
    }
    PAYMENT {
        Long id PK
        String transactionId UK "TXN-..."
        BigDecimal amount
        PaymentMethod paymentMethod
        PaymentStatus status
        LocalDateTime paidAt
        Long booking_id FK "unique (1-1)"
    }
    REVIEW {
        Long id PK
        Integer rating "1..5"
        String title
        String comment
        LocalDate stayDate
        ReviewStatus status "PENDING|APPROVED|REJECTED|FLAGGED"
        Long user_id FK
        Long hotel_id FK
        Long booking_id FK "tùy chọn"
    }
```

Bản ASCII xem nhanh (đọc mũi tên: `1 ──< N` nghĩa là "một-nhiều", `1 ──1` là "một-một"):

```
                 ┌──────────┐
                 │  ADDRESS │
                 └────▲─────┘
                      │ 1-1
   1-N   ┌──────────┐ │        1-N    ┌───────────┐
 ┌──────<│  HOTEL   │─┘   ┌──────────<│ ROOM_TYPE │
 │       └────┬─────┘     │           └───────────┘
 │            │ 1-N       │
 │            ▼           │
 │       ┌──────────┐     │
 │  ┌───<│   ROOM   │>────┘
 │  │    └────┬─────┘
 │  │         │ N-N (qua ROOM_AMENITY)
 │  │         ▼
 │  │    ┌──────────────┐      ┌──────────┐
 │  │    │ ROOM_AMENITY │>─────│ AMENITY  │
 │  │    └──────────────┘  N-N └──────────┘
 │  │
 │  │ 1-N (Room được đặt nhiều lần)
 │  ▼
 │ ┌──────────┐   1-1   ┌──────────┐
 │ │ BOOKING  │────────>│ PAYMENT  │
 │ └────┬─────┘         └──────────┘
 │      │ 1-1 (tùy chọn, review gắn 1 booking)
 │      ▼
 │ ┌──────────┐
 └>│  REVIEW  │<──┐
   └────▲─────┘   │ 1-N
        │ 1-N     │
   ┌────┴─────────┴┐
   │     USER      │  (đặt Booking + viết Review; Hotel cũng 1-N Review)
   └───────────────┘
```

**Điểm cần nhớ khi đọc ERD**:
- `Hotel` và `Address` là **hai bảng riêng**, nối 1-1 qua FK `address_id` (Hotel giữ khóa ngoại, cascade ALL — xóa hotel xóa luôn address).
- `Room ↔ Amenity` là quan hệ **nhiều-nhiều**, hiện thực bằng bảng nối `ROOM_AMENITY` với **khóa chính kép** (`room_id` + `amenity_id`, dùng `@IdClass`).
- `Booking ↔ Payment` là **1-1** (mỗi đơn tối đa một payment; `booking_id` unique).
- `Review` có FK `booking_id` **tùy chọn** (nullable): có thể đánh giá kèm booking hoặc đánh giá "trơn".
- Mọi entity kế thừa `BaseEntity` nên đều có thêm `id`, `createdAt`, `updatedAt` (không vẽ lại cho gọn).

### 2.2. Bảng thực thể

| Entity | Vai trò | Quan hệ chính |
|--------|---------|----------------|
| `User` | Người dùng (implements `UserDetails`) | 1-N `Booking`, 1-N `Review` |
| `Hotel` | Khách sạn | 1-1 `Address` (FK `address_id`), 1-N `Room`, 1-N `Review` |
| `RoomType` | Loại phòng (giá cơ bản, sức chứa) | 1-N `Room` |
| `Room` | Phòng cụ thể trong khách sạn | N-1 `Hotel`, N-1 `RoomType`, N-N `Amenity` qua `RoomAmenity` |
| `Amenity` | Tiện nghi | N-N `Room` qua `RoomAmenity` |
| `RoomAmenity` | Bảng nối Room ↔ Amenity | khóa chính kép (`IdClass`) |
| `Booking` | Đơn đặt phòng | N-1 `User`, N-1 `Room`, 1-1 `Payment` |
| `Payment` | Thanh toán cho một booking | 1-1 `Booking` |
| `Review` | Đánh giá khách sạn | N-1 `User`, N-1 `Hotel`, tùy chọn 1-1 `Booking` |

### Audit timestamp

`BaseEntity` dùng `@CreatedDate` / `@LastModifiedDate`. Cơ chế này được kích hoạt bởi `@EnableJpaAuditing` trong `JpaConfig`. **Nếu thiếu annotation này, mọi lần lưu entity sẽ fail vì `created_at` NULL** — đây là điểm cấu hình bắt buộc.

### Các enum trạng thái

- `UserRole`: `CUSTOMER`, `HOTEL_MANAGER`, `ADMIN`
- `RoomStatus`: `AVAILABLE`, `OCCUPIED`, `MAINTENANCE`, `OUT_OF_SERVICE`
- `BookingStatus`: `PENDING → CONFIRMED → CHECKED_IN → CHECKED_OUT`, hoặc `CANCELLED` / `NO_SHOW`
- `PaymentStatus`: `PENDING`, `PROCESSING`, `COMPLETED`, `FAILED`, `REFUNDED`, `PARTIALLY_REFUNDED`
- `PaymentMethod`: `CREDIT_CARD`, `DEBIT_CARD`, `PAYPAL`, `BANK_TRANSFER`, `CASH`
- `ReviewStatus`: `PENDING` (chờ duyệt), `APPROVED` (hiển thị công khai), `REJECTED` (từ chối), `FLAGGED` (bị gắn cờ)
- `AmenityType`: `HOTEL_LEVEL`, `ROOM_LEVEL`, `BOTH`

---

## 3. Bảo mật & phân quyền

### 3.1. Kiến trúc xác thực (JWT stateless)

```
Client                    Server
  │  POST /auth/login        │
  │─────────────────────────>│  AuthenticationManager xác thực (BCrypt)
  │                          │  JwtService sinh access + refresh token
  │  {accessToken,           │  refresh token: lưu jti vào Redis (TTL)
  │   refreshToken}          │
  │<─────────────────────────│
  │                          │
  │  GET /api/... (Bearer)   │
  │─────────────────────────>│  JwtAuthenticationFilter:
  │                          │   - verify chữ ký + hạn
  │                          │   - ép đúng loại ACCESS_TOKEN
  │                          │   - set SecurityContext (username + roles)
  │<─────────────────────────│
```

- **Session STATELESS**: không có HttpSession, mỗi request tự mang token.
- **`JwtAuthenticationFilter`** (chạy trước `UsernamePasswordAuthenticationFilter`): đọc header `Authorization: Bearer <token>`. Token hợp lệ → set `SecurityContext`. Token hỏng/hết hạn → **không chặn ngay**, để `SecurityFilterChain` quyết định (endpoint public vẫn qua, endpoint cần auth trả 401).
- **`User` implements `UserDetails`**: `getUsername()` trả email, `getAuthorities()` trả `ROLE_<role>`.

### 3.2. Refresh token rotation + reuse detection

- Mỗi refresh token có một `jti` (định danh riêng), được lưu vào Redis với key `{userId}:{jti}`, TTL = hạn refresh token.
- Refresh token chỉ hợp lệ khi **jti của nó còn tồn tại trong Redis**.
- **Luồng refresh** (`POST /auth/refresh`):
  1. Parse + verify refresh token.
  2. Kiểm tra jti còn trong Redis không.
     - Nếu **không còn** → token đã dùng rồi hoặc bị đánh cắp → **revoke toàn bộ phiên của user** (`revokeAllRefreshTokens`) và bắt đăng nhập lại.
  3. Nếu hợp lệ → revoke jti cũ (rotation) + phát hành cặp token mới.
- **Logout** (`POST /auth/logout`): parse refresh token, xóa jti khỏi Redis. Idempotent — token đã hỏng vẫn trả 204.

### 3.3. Phân quyền hai tầng

**Tầng 1 — URL level** (`AppConfig.filterChain`):
- **Public** (không cần đăng nhập):
  - `/auth/**` (login, refresh, logout)
  - `POST /api/users` (đăng ký tài khoản)
  - Swagger: `/swagger-ui.html`, `/swagger-ui/**`, `/v3/api-docs/**`
  - `GET` duyệt công khai: `/api/hotels/**`, `/api/rooms/**`, `/api/room-types/**`, `/api/amenities/**`, `/api/reviews/hotel/*`
- Mọi request khác: cần đăng nhập.

**Tầng 2 — Method level** (`@PreAuthorize`, bật bởi `@EnableMethodSecurity`):
- Create/Update hotel, room, room-type, amenity → `ADMIN` hoặc `HOTEL_MANAGER`
- Delete hotel, room-type, amenity, user + list all users → `ADMIN`
- Delete room, gán/gỡ amenity cho phòng → `ADMIN` / `HOTEL_MANAGER`

**Tầng 3 — Ownership check trong service** (không thể diễn đạt bằng role đơn thuần):
- Booking/Payment: chủ đơn xem/sửa được đơn của mình; ADMIN/HOTEL_MANAGER xem tất cả.
- Review: chỉ tác giả hoặc ADMIN được sửa/xóa; duyệt review cần ADMIN/HOTEL_MANAGER.

### 3.4. Seed tài khoản admin

`DataInitializer` (`CommandLineRunner`) tạo tài khoản ADMIN mặc định lúc khởi động nếu chưa tồn tại. Cấu hình qua `app.admin.email` / `app.admin.password` (mặc định `admin@hotelbooking.com` / `Admin@12345`). Đây là cách có được tài khoản quản trị đầu tiên để dùng các API phân quyền.

### 3.5. Chuỗi Servlet Filter (chạy trước Controller)

Mỗi request đi qua các filter theo đúng thứ tự dưới đây trước khi tới controller. Tất cả đều là `OncePerRequestFilter` (chỉ chạy một lần / request).

```
Request
  │
  ▼
[1] CorrelationIdFilter          (@Order HIGHEST_PRECEDENCE — sớm nhất)
  │    ├─ Đọc header X-Correlation-Id, không có thì sinh UUID
  │    ├─ Đưa correlationId vào MDC (mọi log sau đó tự kèm ID)
  │    ├─ Trả header X-Correlation-Id về client
  │    └─ Sau khi xử lý xong: log "METHOD URI -> status (Xms)" rồi clear MDC
  ▼
--- Security filter chain (AppConfig.filterChain) ---
  │
[2] JwtAuthenticationFilter      (addFilterBefore UsernamePasswordAuthenticationFilter)
  │    ├─ Đọc Bearer token, verify + ép loại ACCESS_TOKEN
  │    └─ Hợp lệ → set SecurityContext (username + roles)
  ▼
[3] RateLimitFilter              (addFilterAfter JwtAuthenticationFilter)
  │    ├─ Chạy SAU Jwt để biết user đã xác thực (nếu có)
  │    ├─ Định danh: "user:{email}" nếu đã đăng nhập, ngược lại "ip:{IP}"
  │    ├─ Redis fixed-window counter (INCR + TTL); vượt ngưỡng → 429
  │    └─ 429 trả ErrorResponse JSON + header Retry-After
  ▼
Controller
```

- **`CorrelationIdFilter`**: gắn ID để trace log xuyên suốt một request; đây cũng là nơi log dòng access log kèm thời gian xử lý. Chạy ngoài security chain, sớm nhất, để cả log lỗi 401/429 cũng có correlation ID. Muốn thấy ID trong log cần thêm `%X{correlationId}` vào logging pattern.
- **`RateLimitFilter`**: giới hạn số request theo cửa sổ cố định (fixed window) trên Redis. Đặt **sau** `JwtAuthenticationFilter` để giới hạn theo user (chính xác hơn theo IP khi nhiều user chung IP). Vì là `@Component`, Spring Boot mặc định tự đăng ký nó như filter chạy **ngoài** security chain — đã tắt bằng `FilterRegistrationBean(enabled=false)` để nó chỉ chạy đúng một lần tại vị trí trong chain. Cấu hình qua `app.rate-limit.max-requests` (mặc định 100) và `app.rate-limit.window-seconds` (mặc định 60).

---

## 4. Các luồng nghiệp vụ

### 4.1. Đăng ký & đăng nhập

```
POST /api/users        → tạo CUSTOMER (email unique, password mã hóa BCrypt)
POST /auth/login       → trả accessToken + refreshToken
POST /auth/refresh     → xoay token (rotation)
POST /auth/logout      → revoke refresh token
```

- Đăng ký chỉ tạo được role `CUSTOMER`. Role `ADMIN`/`HOTEL_MANAGER` do admin cấp (hoặc seed).

### 4.2. Quản lý khách sạn & phòng (ADMIN / HOTEL_MANAGER)

```
Hotel  → Room  → gán Amenity
   │        │
RoomType chia sẻ giá cơ bản; Room có pricePerNight riêng.
```

- Tạo `Hotel` (kèm `Address`, giờ check-in/out mặc định 14:00 / 12:00).
- Tạo `RoomType` (loại phòng dùng chung, tên unique).
- Tạo `Room` gắn với 1 Hotel + 1 RoomType; `roomNumber` unique trong cùng khách sạn.
- Gán/gỡ tiện nghi: `POST/DELETE /api/rooms/{id}/amenities/{amenityId}`.

### 4.3. Tạo phòng (Room) — ADMIN / HOTEL_MANAGER

`POST /api/rooms` → `RoomService.createRoom`. Trình tự kiểm tra:

```
POST /api/rooms  (ADMIN / HOTEL_MANAGER)
   │
   ├─ Hotel tồn tại?          (không → 404 ResourceNotFound)
   ├─ RoomType tồn tại?       (không → 404 ResourceNotFound)
   ├─ roomNumber unique trong hotel?  (trùng → 409 DuplicateResource, ràng buộc uk_hotel_room_number)
   └─ status = giá trị truyền vào, mặc định AVAILABLE nếu bỏ trống
```

- **Số phòng chỉ unique trong phạm vi một khách sạn** — hai khách sạn khác nhau có thể cùng có phòng "101".
- **`Room.pricePerNight` độc lập với `RoomType.basePrice`**: booking tính tiền theo `pricePerNight` của phòng cụ thể, không dùng `basePrice` của loại phòng. `basePrice` chỉ mang tính tham khảo/giá gốc.
- **Update** (`PUT /api/rooms/{id}`): chỉ kiểm tra lại tính unique của `roomNumber` khi nó thay đổi; các field khác patch nếu khác null.
- **Delete** (`DELETE /api/rooms/{id}`): **hard-delete**, hiện chưa kiểm tra phòng còn booking hay không (xem [Mục 4.7 — Gaps](#47-các-điểm-logic-chưa-chặt-known-gaps)).
- **Gán/gỡ tiện nghi**: `POST/DELETE /api/rooms/{id}/amenities/{amenityId}` qua bảng nối `RoomAmenity`, có kiểm tra tồn tại + chống trùng.

### 4.4. Đặt phòng (Booking)

`POST /api/bookings` → `BookingService.createBooking`. Người dùng đã đăng nhập (thường là CUSTOMER); `customer` lấy từ email trong JWT (`principal.getName()`).

```
POST /api/bookings  (cần đăng nhập)
   │
   ├─ 1. Room KHÔNG ở trạng thái MAINTENANCE / OUT_OF_SERVICE   (nếu có → 400)
   ├─ 2. KHÔNG trùng lịch (existsOverlappingBooking, excludeId=null)  (trùng → 400)
   ├─ 3. Số đêm = ChronoUnit.DAYS.between(checkIn, checkOut)
   ├─ 4. totalAmount = room.pricePerNight × số đêm
   ├─ 5. Sinh bookingReference "BK-XXXXXXXX" (lặp tới khi unique)
   └─ 6. Lưu booking với status = PENDING
```

**Bước 1 — cổng trạng thái phòng**: chỉ `MAINTENANCE` và `OUT_OF_SERVICE` chặn đặt phòng. Lưu ý `OCCUPIED` **không** chặn ở bước này — phòng có "trống" hay không hoàn toàn dựa vào kiểm tra trùng lịch (bước 2), không dựa vào `Room.status`.

**Bước 2 — quy tắc trùng lịch** (`existsOverlappingBooking`): một phòng bị coi là đã đặt nếu tồn tại booking thỏa **cả ba** điều kiện:
1. cùng `room_id`,
2. status ∈ {`PENDING`, `CONFIRMED`, `CHECKED_IN`} (các trạng thái "đang giữ phòng"),
3. khoảng ngày giao nhau theo kiểu **nửa mở**: `existingCheckIn < newCheckOut` **và** `existingCheckOut > newCheckIn`.

Hệ quả:
- Booking `CANCELLED` / `CHECKED_OUT` / `NO_SHOW` **không** chặn → phòng tự động trống lại.
- Cho phép **trả phòng và nhận phòng trong cùng một ngày** (khách A trả 25/7, khách B nhận 25/7 → không tính là trùng).

**Bước 4 — tính giá**: dùng `Room.pricePerNight`, không dùng `RoomType.basePrice`.

**Vòng đời booking**:
```
PENDING ──(thanh toán COMPLETED / manager đổi status)──> CONFIRMED
   │                                                          │
   │                                              CHECKED_IN → CHECKED_OUT
   └──(hủy)──> CANCELLED                          (NO_SHOW: đặt thủ công)
```

- **Update** (`PUT /api/bookings/{id}`): **chỉ sửa được khi còn `PENDING`**. Nếu đổi ngày → kiểm tra lại thứ tự (checkOut phải sau checkIn), chạy lại overlap check (loại trừ chính booking này qua `excludeBookingId`) và tính lại `totalAmount`.
- **Hủy** (`POST /{id}/cancel`): chủ đơn hoặc ADMIN/HOTEL_MANAGER. **Không hủy được** nếu đã `CHECKED_IN` / `CHECKED_OUT`, hoặc đã `CANCELLED` (chống hủy hai lần).

### 4.5. Nhận phòng & trả phòng (Check-in / Check-out)

**Không có endpoint riêng.** Nhận/trả phòng thực hiện qua API đổi trạng thái chung:

```
PATCH /api/bookings/{id}/status?status=CHECKED_IN    → nhận phòng
PATCH /api/bookings/{id}/status?status=CHECKED_OUT   → trả phòng
```

`BookingService.updateBookingStatus`:
- **Chỉ ADMIN / HOTEL_MANAGER** được gọi (lễ tân/quản lý thao tác, không phải khách).
- **Không có state-machine validation**: người có quyền chuyển booking sang **bất kỳ** trạng thái nào, kể cả nhảy thẳng sang `CHECKED_OUT` mà chưa `CHECKED_IN`. Đây là cơ chế check-in/check-out de-facto (xem [Mục 4.7 — Gaps](#47-các-điểm-logic-chưa-chặt-known-gaps)).
- **`Room.status` không tự đồng bộ** khi check-in/check-out — phòng không tự chuyển sang `OCCUPIED` khi có khách nhận. Việc quản lý phòng trống chỉ dựa vào overlap query trên booking.

### 4.6. Thanh toán (Payment)

```
POST /api/payments  (chủ booking hoặc admin/manager)
   │
   ├─ Booking không được CANCELLED         (nếu là → 400)
   ├─ Mỗi booking chỉ 1 payment (1-1)      (đã có → 409 DuplicateResource)
   ├─ Sinh transactionId "TXN-..." (unique)
   ├─ amount = booking.totalAmount
   └─ status = PENDING

PATCH /api/payments/{id}/status  (ADMIN / HOTEL_MANAGER)
   │
   └─ Nếu COMPLETED:
        ├─ set paidAt = now
        └─ nếu booking đang PENDING → tự động chuyển CONFIRMED
```

**Cầu nối giữa hai domain**: thanh toán chuyển sang `COMPLETED` là tác nhân duy nhất tự động xác nhận booking (`PENDING → CONFIRMED`). Các trạng thái payment khác (`PROCESSING`, `FAILED`, `REFUNDED`...) không có side-effect lên booking.

### 4.7. Các điểm logic chưa chặt (Known gaps)

Những chỗ dưới đây hiện chưa được enforce trong code — ghi lại để tránh hiểu nhầm là đã có, và làm danh sách việc cần cân nhắc:

1. **Không kiểm tra số khách vs sức chứa**: `numberOfGuests` không được validate với `RoomType.maxOccupancy` khi đặt phòng.
2. **`updateBookingStatus` không có state machine**: có thể chuyển trạng thái tùy ý (VD: sang `CHECKED_OUT` mà chưa qua `CHECKED_IN`). Chỉ `cancelBooking` mới có guard chuyển trạng thái.
3. **`Room.status` không đồng bộ với vòng đời booking**: check-in không làm phòng thành `OCCUPIED`; `OCCUPIED` được định nghĩa nhưng không dùng trong logic đặt phòng.
4. **`deleteRoom` hard-delete không kiểm tra ràng buộc**: xóa phòng không kiểm tra booking đang tồn tại → có thể lỗi khóa ngoại hoặc để lại booking mồ côi tùy ràng buộc DB.

### 4.8. Đánh giá (Review)

```
POST /api/reviews  (cần đăng nhập)
   │
   ├─ Nếu có bookingId:
   │    ├─ booking phải thuộc về user
   │    ├─ booking phải thuộc đúng hotel được review
   │    └─ mỗi booking chỉ review 1 lần
   └─ status = PENDING (chờ duyệt)

Duyệt: PATCH /api/reviews/{id}/status  (ADMIN / HOTEL_MANAGER)
```

- **Đọc công khai**: `GET /api/reviews/hotel/{hotelId}` chỉ trả review `APPROVED`.
- **Đọc toàn bộ** (gồm PENDING/FLAGGED): `GET /api/reviews/hotel/{hotelId}/all` — chỉ ADMIN/HOTEL_MANAGER.
- **Sửa/xóa**: chỉ tác giả hoặc ADMIN. Khi sửa, status reset về `PENDING` (duyệt lại).

### 4.9. Phân trang (Pagination)

Các endpoint trả về danh sách có thể phình to đều nhận `Pageable` (Spring tự bind từ query param) và trả `PageResponse<T>` thay vì `List<T>` trần.

**Endpoint đã phân trang**:
- Booking: `GET /api/bookings`, `GET /api/bookings/my`
- Room: `GET /api/rooms`, `GET /api/rooms/hotel/{hotelId}`
- Hotel: `GET /api/hotels`
- Review: `GET /api/reviews/hotel/{hotelId}`, `GET /api/reviews/hotel/{hotelId}/all`, `GET /api/reviews/my`

Các endpoint tra cứu nhỏ (RoomType, Amenity) và endpoint chi tiết (`GET /{id}`) giữ nguyên, không phân trang.

**Query param** (chuẩn Spring Data):
```
GET /api/rooms?page=0&size=20&sort=pricePerNight,asc
```
- `page`: số trang, bắt đầu từ 0 (mặc định 0).
- `size`: số phần tử mỗi trang (mặc định 20).
- `sort`: `field,asc|desc` (lặp lại nhiều lần để sort đa tiêu chí).

**Cấu trúc `PageResponse<T>`** (không trả `PageImpl` trực tiếp vì Spring cảnh báo định dạng serialize không ổn định giữa các phiên bản):
```json
{
  "content": [ ... ],
  "page": 0,
  "size": 20,
  "totalElements": 135,
  "totalPages": 7,
  "first": true,
  "last": false
}
```
`PageResponse.of(Page<E>, Function<E,T>)` map từng entity sang DTO đồng thời giữ nguyên metadata phân trang.

---

## 5. Xử lý lỗi tập trung

`GlobalExceptionHandler` (`@RestControllerAdvice`) ánh xạ exception → HTTP status, trả `ErrorResponse` thống nhất (`timestamp`, `status`, `error`, `message`, `path`, `validationErrors`):

| Exception | HTTP Status |
|-----------|-------------|
| `ResourceNotFoundException` | 404 Not Found |
| `DuplicateResourceException` | 409 Conflict |
| `AccessDeniedException` | 403 Forbidden |
| `IllegalArgumentException` | 400 Bad Request |
| `AuthenticationException` | 401 Unauthorized ("Invalid credentials") |
| `JwtException` | 401 Unauthorized |
| `MethodArgumentNotValidException` | 400 + map lỗi từng field |
| `Exception` (còn lại) | 500 Internal Server Error |

> **Ngoài `GlobalExceptionHandler`**: `RateLimitFilter` chạy trong filter chain (trước khi tới controller/`@RestControllerAdvice`) nên tự ghi `ErrorResponse` cùng định dạng với **429 Too Many Requests**, kèm header `Retry-After`.

---

## 6. Bảng endpoint

| Nhóm | Endpoint | Quyền |
|------|----------|-------|
| Auth | `POST /auth/login`, `/refresh`, `/logout` | Public |
| User | `POST /api/users` | Public (đăng ký) |
| User | `GET /api/users`, `DELETE /api/users/{id}` | ADMIN |
| User | `GET /api/users/{id}` | Đăng nhập |
| Hotel | `GET /api/hotels/**` | Public |
| Hotel | `POST`, `PUT` | ADMIN / HOTEL_MANAGER |
| Hotel | `DELETE` | ADMIN |
| RoomType | `GET /api/room-types/**` | Public |
| RoomType | `POST`, `PUT` | ADMIN / HOTEL_MANAGER |
| RoomType | `DELETE` | ADMIN |
| Room | `GET /api/rooms/**` | Public |
| Room | `POST`, `PUT`, `DELETE`, gán/gỡ amenity | ADMIN / HOTEL_MANAGER |
| Amenity | `GET /api/amenities/**` | Public |
| Amenity | `POST`, `PUT` | ADMIN / HOTEL_MANAGER |
| Amenity | `DELETE` | ADMIN |
| Booking | `POST`, `GET /my`, `GET /{id}`, `PUT`, `/cancel` | Đăng nhập (ownership check) |
| Booking | `GET /api/bookings`, `PATCH /{id}/status` | ADMIN / HOTEL_MANAGER |
| Payment | `POST`, `GET /{id}`, `GET /booking/{id}` | Đăng nhập (ownership check) |
| Payment | `GET /api/payments`, `PATCH /{id}/status` | ADMIN / HOTEL_MANAGER |
| Review | `GET /hotel/{id}` | Public (chỉ APPROVED) |
| Review | `POST`, `GET /my`, `PUT`, `DELETE` | Đăng nhập (ownership check) |
| Review | `GET /hotel/{id}/all`, `PATCH /{id}/status` | ADMIN / HOTEL_MANAGER |

---

## 7. Cấu hình & vận hành

- **Database**: PostgreSQL (`jdbc:postgresql://localhost:5432/HotelBooking`), `ddl-auto: update`. Test dùng H2 in-memory (`create-drop`, profile `test`).
- **Redis**: `localhost:6379` — lưu jti refresh token và bộ đếm rate limit (`rate-limit:*`).
- **JWT**: `jwt.secretKey`, access token 10 phút (`600000` ms), refresh token 7 ngày (`604800000` ms).
- **Rate limit**: `app.rate-limit.max-requests` (mặc định `100`) request trên mỗi `app.rate-limit.window-seconds` (mặc định `60`) giây, tính theo user đã đăng nhập hoặc IP. Xem [Mục 3.5](#35-chuỗi-servlet-filter).
- **Swagger UI**: `http://localhost:8080/swagger-ui.html` (đã cấu hình JWT bearer scheme).

> **Lưu ý bảo mật khi deploy**: `jwt.secretKey` và `app.admin.password` đang để giá trị mặc định trong `application.yaml`. Trước khi lên production nên chuyển sang biến môi trường và đổi giá trị.
