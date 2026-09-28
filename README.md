# HotelBooking - Hệ thống đặt phòng khách sạn

## Mô tả dự án

HotelBooking là một ứng dụng web cung cấp hệ thống đặt phòng khách sạn trực tuyến, được xây dựng bằng Spring Boot 4.1.0 và Java 17. Hệ thống cho phép người dùng tìm kiếm khách sạn, xem thông tin phòng, đặt phòng, thanh toán và quản lý các đặt phòng của mình. Đồng thời hỗ trợ các chức năng quản lý cho admin và quản lý khách sạn.

## Công nghệ sử dụng

- **Ngôn ngữ**: Java 17
- **Framework**: Spring Boot 4.1.0
- **Cơ sở dữ liệu**: PostgreSQL
- **Cache**: Redis
- **Authentication**: JWT (JSON Web Token)
- **ORM**: Spring Data JPA
- **Bảo mật**: Spring Security
- **API Documentation**: Springdoc OpenAPI (Swagger UI)
- **Validation**: Jakarta Validation
- **Build Tool**: Gradle
- **Thư viện hỗ trợ**: Lombok, Jackson, HHhho

## Tính năng chính

### Đối với người dùng thường
- Đăng ký/đăng nhập tài khoản
- Xem danh sách khách sạn và phòng có sẵn
- Tìm kiếm phòng theo ngày, giá, tiện ích
- Đặt phòng, hủy đặt phòng
- Xem lịch sử đặt phòng
- Thanh toán trực tuyến
- Đánh giá, phản hồi sau khi ở

### Đối với quản trị viên (ADMIN)
- Quản lý người dùng (khách hàng, quản lý khách sạn)
- Quan hệ thống tổng quan
- Quản lý khách sạn (thêm, sửa, xóa)
- Quan hệ thống thống kê

### Đối với quản lý khách sạn (HOTEL_MANAGER)
- Quản lý thông tin khách sạn của mình
- Quản lý loại phòng và tiện ích
- Quản lý đặt phòng thuộc về khách sạn của mình
- Xác nhận/ từ chối yêu cầu đặt phòng
- Theo kiếm doanh thu

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/example/hotelbooking/
│   │   ├── controller      # REST Controllers
│   │   ├── model          # Entities, DTOs, Enums
│   │   │   ├── entity     # JPA Entities
│   │   │   ├── dto        # Data Transfer Objects
│   │   │   ├── enums      # Enumeration classes
│   │   │   └── base       # Base classes
│   │   ├── repository     # Spring Data Repositories
│   │   ├── service        # Business Logic
│   │   └── exception      # Custom Exceptions
│   └── resources/
│       ├── application.yaml     # Cấu hình ứng dụng
│       └── application-test.yaml # Cấu hình test
└── test/
    └── java/com/example/hotelbooking/ # Tests
```


## API Documentation

API documentation được tạo tự động bằng Springdoc OpenAPI và có thể truy cập tại:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Các nhóm API chính:
- **Auth**: Xác thực người dùng (đăng nhập, refresh token)
- **Users**: Quản lý người dùng
- **Hotels**: Quản lý khách sạn
- **Rooms**: Quản lý phòng và loại phòng
- **Bookings**: Quản lý đặt phòng (tạo, lấy, cập nhật, hủy)
- **Payments**: Xử lý thanh toán
- **Reviews**: Quản lý đánh giá, phản hồi
- **Amenities**: Quản lý tiện ích

## Các tính năng kỹ thuật đặc biệt

### Redis Caching
Áp dụng caching cho các truy vấn thường xuyên như:
- Danh sách khách sạn
- Thông tin phòng có sẵn
- Chi tiết khách sạn/phòng

### Bảo mật
- Sử dụng JWT cho stateless authentication
- Mã hóa mật khẩu bằng BCrypt
- Quyền truy cập dựa trên vai trò (ROLE_USER, ROLE_HOTEL_MANAGER, ROLE_ADMIN)
- Bảo vệ endpoints bằng Spring Security

### Validation
- Sử dụng Jakarta Validation để validate đầu vào API
- Custom validation constraints cho các trường đặc殊

### Xử lý lỗi
- Centralized exception handling
- Đjednoc định dạng phản hồi lỗi
- Các exception tùy chỉnh: ResourceNotFoundException, DuplicateResourceException

