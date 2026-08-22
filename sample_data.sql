-- Sample data for HotelBooking database
-- 15 records for each table

-- Address table
INSERT INTO address (id, street_address, city, state, created_at, updated_at) VALUES
(1, '123 Trần Phú Street', 'Hà Nội', 'Hà Nội', NOW(), NOW()),
(2, '456 Lê Lợi Avenue', 'Đà Nẵng', 'Đà Nẵng', NOW(), NOW()),
(3, '789 Nguyễn Huệ Boulevard', 'TP.HCM', 'TP.HCM', NOW(), NOW()),
(4, '321 Hải Bà Trưng Street', 'Huế', 'Thừa Thiên Huế', NOW(), NOW()),
(5, '654 Trần Hưng Đạo Road', 'Hải Phòng', 'Hải Phòng', NOW(), NOW()),
(6, '987 Phan Chu Trinh Street', 'Nha Trang', 'Khánh Hòa', NOW(), NOW()),
(7, '147 Hoàng Diệu Street', 'Cần Thơ', 'Cần Thơ', NOW(), NOW()),
(8, '258 Nguyễn Trãi Street', 'Đà Lạt', 'Lâm Đồng', NOW(), NOW()),
(9, '369 Lê Duẩn Avenue', 'Vinh', 'Nghệ An', NOW(), NOW()),
(10, '741 Trần Phú Street', 'Quy Nhơn', 'Bình Định', NOW(), NOW()),
(11, '852 Hà Nội Street', 'Hà Giang', 'Hà Giang', NOW(), NOW()),
(12, '963 Huế Street', 'Kon Tum', 'Kon Tum', NOW(), NOW()),
(13, '159 Đà Nẵng Street', 'Buôn Ma Thuột', 'Đăk Lăk', NOW(), NOW()),
(14, '357 Nha Trang Street', 'Rạch Giá', 'Kiên Giang', NOW(), NOW()),
(15, '951 Cần Thơ Street', 'Sóc Trăng', 'Sóc Trăng', NOW(), NOW());

-- Amenity table
INSERT INTO amenity (id, name, description, icon_name, type, created_at, updated_at) VALUES
(1, 'WiFi miễn phí', 'WiFi tốc độ cao miễn phí trong toàn bộ khu vực khách sạn', 'wifi', 'HOTEL_LEVEL', NOW(), NOW()),
(2, 'Bể bơi ngoài trời', 'Bể bơi lớn với view đẹp, thích hợp cho việc thư giãn', 'pool', 'HOTEL_LEVEL', NOW(), NOW()),
(3, 'Phòng gym hiện đại', 'Phòng tập thể dục wyposaż đầy đủ thiết bị hiện đại', 'gym', 'HOTEL_LEVEL', NOW(), NOW()),
(4, 'Nhà hàng trong客 sạn', 'Nhà hàng phục vụ ẩm thực địa preparedẩm thực quốc tế', 'restaurant', 'HOTEL_LEVEL', NOW(), NOW()),
(5, 'Bar nước uống', 'Bar phục vụ các loại cocktail và đồ uống része', 'bar', 'HOTEL_LEVEL', NOW(), NOW()),
(6, 'Spa và dịch vụ massage', 'Dịch vụ spa và massage chuyên nghiệp để thư giãn cơ thể', 'spa', 'HOTEL_LEVEL', NOW(), NOW()),
(7, 'Đỗ xe miễn phí', 'Bãi đỗ xe rộng rãi miễn phí cho khách hàng', 'parking', 'HOTEL_LEVEL', NOW(), NOW()),
(8, 'Dịch vụ đưa đón sân bay', 'Dịch vụ taxi riêng đưa đón từ sân bay tới khách sạn', 'airport-shuttle', 'HOTEL_LEVEL', NOW(), NOW()),
(9, 'Phòng họp và tiễn nghị', 'Phòng họp đầy đủ thiết bị cho công việc và tổ chức sự kiện', 'meeting-room', 'HOTEL_LEVEL', NOW(), NOW()),
(10, 'TiV màn hình plat', 'TiV màn hình plat với kênhs c능 उपलब्ध trong phòng', 'tv', 'ROOM_LEVEL', NOW(), NOW()),
(11, 'Máy lạnhчиворегулируемый', 'Hệ thống điều hòa không khí với điều khiển nhiệt độ trong phòng', 'ac', 'ROOM_LEVEL', NOW(), NOW()),
(12, 'Bộ ch légales giường премиум', 'Bộ ch 황제 das và giường nghê качественное для ngủ ngon', 'bedding', 'ROOM_LEVEL', NOW(), NOW()),
(13, 'Mini bar trong phòng', 'Mini bar được wyposaż sẵn các loại đồ uống và ăn vặt', 'minibar', 'ROOM_LEVEL', NOW(), NOW()),
(14, 'Ban công riêng', 'Ban công riêng tư với view đẹp cho mỗi phòng', 'balcony', 'ROOM_LEVEL', NOW(), NOW()),
(15, 'Dịch vụ giặt ��', 'Dịch vụ giặt �� và prawania 옷을 швидко та якісно', 'laundry', 'HOTEL_LEVEL', NOW(), NOW());

-- RoomType table
INSERT INTO room_type (id, name, description, max_occupancy, bed_type, number_of_beds, size_square_meters, base_price, created_at, updated_at) VALUES
(1, 'Phòng Standard', 'Phòng đơn giản nhưng đủ tiện nghi cần thiết', 2, 'Đơn', 1, 25.0, 500000.00, NOW(), NOW()),
(2, 'Phòng Double', 'Phòng với giường đôi lớn, thích hợp cho cặp đôi', 2, 'Đôi', 1, 30.0, 600000.00, NOW(), NOW()),
(3, 'Phòng Triple', 'Phòng với 3 giường đơn, thích hợp cho gia đình kecil', 3, 'Đơn', 3, 35.0, 750000.00, NOW(), NOW()),
(4, 'Phòng Family', 'Phòng gia đình với giường lớn và sofá giường', 4, 'Đôi + Sofá', 2, 45.0, 900000.00, NOW(), NOW()),
(5, 'Phòng Suite', 'Phòngsuite sang trọng với phòng khách và ngủ tách rời', 2, 'Đôi', 1, 55.0, 1200000.00, NOW(), NOW()),
(6, 'Phòng Deluxe View', 'Phòng đẹp với view thành phố hoặc biển', 2, 'Đôi', 1, 40.0, 800000.00, NOW(), NOW()),
(7, 'Phòng Phù Júnior', 'Phòng spacious với diện tích rộng và tiện nghi sang trọng', 3, 'Đôi + Đơn', 2, 50.0, 1000000.00, NOW(), NOW()),
(8, 'Phòng Penthouse', 'Phòng hàng đầu trên tầng cao nhất với view panoramic', 4, 'Đôi', 2, 80.0, 2000000.00, NOW(), NOW()),
(9, 'Phòng доступный для колясочников', 'Phòng được thiết kế đặc biệt cho người khuyết tật di chuyển', 2, 'Đơn', 1, 35.0, 550000.00, NOW(), NOW()),
(10, 'Phòng Honeymoon', 'Phòng roмантичecкий với thiết kế đặc sắc cho nguyệt sanctum', 2, 'Đôi', 1, 45.0, 1500000.00, NOW(), NOW()),
(11, 'Phòng Kinh doanh', 'Phòng được thiết kế dành cho khách công việc với making việc', 1, 'Đơn', 1, 30.0, 700000.00, NOW(), NOW()),
(12, 'Phòng Gia đình lớn', 'Phòng rộng rãi thích hợp cho gia đình có nhiều thành viên', 6, 'Đôi + 2xĐơn', 3, 60.0, 1300000.00, NOW(), NOW()),
(13, 'Phòng Eco Friendly', 'Phòng được thiết kế thân thiện với môi trường, dùng vật liệu tự nhiên', 2, 'Đơn', 1, 30.0, 650000.00, NOW(), NOW()),
(14, 'Phòng предупреждения шума', 'Phòng cách âm đặc biệt để zapewnić maksymalny spokój i ciszę', 2, 'Đôi', 1, 35.0, 700000.00, NOW(), NOW()),
(15, 'Phòng Президентскийлюкс', 'Phòng sang trọng nhất với tiện nghi 최고 undez gredients duy nhất', 4, 'Đôi + Sofá', 3, 100.0, 3000000.00, NOW(), NOW());

-- User table
INSERT INTO "user" (id, email, password, first_name, last_name, phone_number, role, enabled, created_at, updated_at) VALUES
(1, 'customer1@example.com', '$2a$10$hashedpassword1', 'Nguyễn Văn', 'An', '0901234567', 'CUSTOMER', true, NOW(), NOW()),
(2, 'customer2@example.com', '$2a$10$hashedpassword2', 'Trần Thị', 'Bình', '0902345678', 'CUSTOMER', true, NOW(), NOW()),
(3, 'customer3@example.com', '$2a$10$hashedpassword3', 'Lê Hoàng', 'Cường', '0903456789', 'CUSTOMER', true, NOW(), NOW()),
(4, 'customer4@example.com', '$2a$10$hashedpassword4', 'Phạm Minh', 'Dương', '0904567890', 'CUSTOMER', true, NOW(), NOW()),
(5, 'customer5@example.com', '$2a$10$hashedpassword5', 'Hoàng Quốc', 'Em', '0905678901', 'CUSTOMER', true, NOW(), NOW()),
(6, 'manager1@example.com', '$2a$10$hashedpassword6', 'Người Quản Lý', 'Khách Sạn 1', '0906789012', 'HOTEL_MANAGER', true, NOW(), NOW()),
(7, 'manager2@example.com', '$2a$10$hashedpassword7', 'Người Quản Lý', 'Khách Sạn 2', '0907890123', 'HOTEL_MANAGER', true, NOW(), NOW()),
(8, 'admin@example.com', '$2a$10$hashedpassword8', 'Quản Trị Viên', 'Hệ Thống', '0908901234', 'ADMIN', true, NOW(), NOW()),
(9, 'customer6@example.com', '$2a$10$hashedpassword9', 'Vũ Thị', 'Phượng', '0909012345', 'CUSTOMER', true, NOW(), NOW()),
(10, 'customer7@example.com', '$2a$10$hashedpassword10', 'Đặng Minh', 'Quân', '0910123456', 'CUSTOMER', true, NOW(), NOW()),
(11, 'customer8@example.com', '$2a$10$hashedpassword11', 'Bùi Tuấn', 'Sáng', '0911234567', 'CUSTOMER', true, NOW(), NOW()),
(12, 'customer9@example.com', '$2a$10$hashedpassword12', 'Hồ Gia', 'Bảo', '0912345678', 'CUSTOMER', true, NOW(), NOW()),
(13, 'customer10@example.com', '$2a$10$hashedpassword13', 'Lý Nguyên', 'Thịnh', '0913456789', 'CUSTOMER', true, NOW(), NOW()),
(14, 'manager3@example.com', '$2a$10$hashedpassword14', 'Trần Bảo', 'Ngọc', '0914567890', 'HOTEL_MANAGER', true, NOW(), NOW()),
(15, 'staff@example.com', '$2a$10$hashedpassword15', 'Nhân Viên', 'Tân Trương', '0915678901', 'CUSTOMER', true, NOW(), NOW());

-- Hotel table
INSERT INTO hotel (id, name, description, star_rating, email, phone_number, check_in_time, check_out_time, enabled, address_id, created_at, updated_at) VALUES
(1, 'Khách Sạn Hàn Quốc', 'Khách sạn 4 sao với thiết kế hiện đại, 위치한 в центре города', 4, 'info@hotelhanquoc.com', '02438765432', '14:00:00', '12:00:00', true, 1, NOW(), NOW()),
(2, 'Khách Sạn Phú Quốc Resort', 'Kh nghỉ dưỡng 5 sao trên đảo Phú Quốc với bể bơi infinity view', 5, 'contact@phuquocresort.com', '02973876543', '14:00:00', '12:00:00', true, 2, NOW(), NOW()),
(3, 'Khách Sạn Dragon Riverside', 'Khách sạn bên sông Hàn với view đẹp và dịch vụ tốt', 4, 'reservation@dragonriverside.com', '02363876543', '14:00:00', '12:00:00', true, 3, NOW(), NOW()),
(4, 'Khách Sạn Heritage Huế', 'Khách sạn francia phong cách cổ điển ở trung tâm Huế', 5, 'info@heritagehue.com', '02343876543', '14:00:00', '12:00:00', true, 4, NOW(), NOW()),
(5, 'Khách Sạn Navy Hai Phong', 'Khách sạn cách biển 200m với przestrzeń и удобства moderna', 3, 'contact@navyhaiphong.com', '02253876543', '14:00:00', '12:00:00', true, 5, NOW(), NOW()),
(6, 'Khách Sạn Sunshine Nha Trang', 'Kh nghỉ dưỡng 4 sao trên bãi biển Nha Trang với sport và giải trí', 4, 'reservation@sunshine-natrang.com', '02583876543', '14:00:00', '12:00:00', true, 6, NOW(), NOW()),
(7, 'Khách Sạn Mekong Delta', 'Khach sạn ở miền Tây với kiến trúc truyền thống và услуга-гостеприимства', 3, 'info@mekongdelta.com', '02913876543', '14:00:00', '12:00:00', true, 7, NOW(), NOW()),
(8, 'Khách Sạn Castello Dalat', 'Khách sạn phong cách lâu đài ở Đà Lạt với không khí mát mẻ và υπέροче', 4, 'contact@castellodalat.com', '02633876543', '14:00:00', '12:00:00', true, 8, NOW(), NOW()),
(9, 'Khách Sạn Ngọc Linh Vinh', 'Khách sạn 3 sao giá tốt với vị trí đẹp ở Vinh', 3, 'reservation@ngoclinhvinh.com', '02383876543', '14:00:00', '12:00:00', true, 9, NOW(), NOW()),
(10, 'Khách Sạn Seaview Quy Nhon', 'Khách sạn view biển đẹp ở Quy Nhơn với bể bơi và центром развлечений', 4, 'info@seaviewquynhon.com', '02563876543', '14:00:00', '12:00:00', true, 10, NOW(), NOW()),
(11, 'Khách Sạn Bắc Bộ Cao Nguyên', 'Khách sạn ở khu vực cao nguyên với cơ sở vật chất tốt và phòng ngơi yên tĩnh', 3, 'contact@bacbocaongo.com', '02163876543', '14:00:00', '12:00:00', true, 11, NOW(), NOW()),
(12, 'Khách Sạn Tengah Tây Nguyên', 'Không nghỉ dưỡng trong rừng với các hoạt động phiêu lưu và ecotourism', 4, 'info@trungtainguyen.com', '02603876543', '14:00:00', '12:00:00', true, 12, NOW(), NOW()),
(13, 'Khách Sạn Cà Phê Buôn Ma Thuột', 'Khách sạn đặc trưng vớiวัฒนธรรม cà phê và không gian làm việc cùng встречая', 3, 'reservation@buonmathuot.coffee.com', '02303876543', '14:00:00', '12:00:00', true, 13, NOW(), NOW()),
(14, 'Khách Sạn đảo Phú Quốc extremo', 'Kh resort 5 sao riêng biệt trên đảo với vilа-тpenнент и сервис экстра-класса', 5, 'contact@phuquocextremo.com', '02973876544', '14:00:00', '12:00:00', true, 14, NOW(), NOW()),
(15, 'Khách Sạn Dong Tay Soc Trang', 'Kh khách sạn레이링 с видом на реку и сферой гостеприимства на высшем уровне', 3, 'info@dongtaysoc.com', '02973876545', '14:00:00', '12:00:00', true, 15, NOW(), NOW());

-- Room table
INSERT INTO room (id, room_number, status, price_per_night, description, hotel_id, room_type_id, created_at, updated_at) VALUES
(1, '101', 'AVAILABLE', 550000.00, 'Phòng estándar с видом на город', 1, 1, NOW(), NOW()),
(2, '102', 'AVAILABLE', 550000.00, 'Phòng estándar с видом на улицу', 1, 1, NOW(), NOW()),
(3, '201', 'AVAILABLE', 650000.00, 'Phòng Double с улучшенной отделкой', 1, 2, NOW(), NOW()),
(4, '202', 'AVAILABLE', 650000.00, 'Phòng Double для новобрачных', 1, 2, NOW(), NOW()),
(5, '301', 'MAINTENANCE', 750000.00, 'Phòng Triple для семей с детьми', 1, 3, NOW(), NOW()),
(6, '302', 'AVAILABLE', 750000.00, 'Phòng Triple с видом на двор', 1, 3, NOW(), NOW()),
(7, '101', 'AVAILABLE', 1800000.00, 'Phòng Luxury Suite с террасой и джакузи', 2, 5, NOW(), NOW()),
(8, '102', 'AVAILABLE', 1600000.00, 'Phòng Deluxe с панорамным видом на океан', 2, 6, NOW(), NOW()),
(9, '201', 'AVAILABLE', 1400000.00, 'Phòng для деловых поездок с офисной зоной', 2, 11, NOW(), NOW()),
(10, '202', 'AVAILABLE', 1200000.00, 'Phòng стандарт повышенной комфортности', 2, 1, NOW(), NOW()),
(11, '301', 'BOOKED', 1000000.00, 'Phòng пространный с зоной отдыха', 2, 7, NOW(), NOW()),
(12, '101', 'AVAILABLE', 450000.00, 'Phòng económica с базовыми удобствами', 3, 1, NOW(), NOW()),
(13, '102', 'AVAILABLE', 500000.00, 'Phòng стандарт со стандартным набором удобств', 3, 1, NOW(), NOW()),
(14, '201', 'AVAILABLE', 600000.00, 'Phòng улучшенной планировки с зоной работы', 3, 2, NOW(), NOW()),
(15, '202', 'AVAILABLE', 550000.00, 'Phòng комфортné для длительного проживания', 3, 4, NOW(), NOW()),
(16, '301', 'AVAILABLE', 700000.00, 'Phòng suite junior с отдельной гостиной', 3, 7, NOW(), NOW(),
(17, '302', 'MAINTENANCE', 800000.00, 'Phòng люкс с гардеробной и мини-баром', 3, 5, NOW(), NOW(),
(18, '401', 'AVAILABLE', 900000.00, 'Phòng famiglia с двумя спальнями и гостиной', 3, 4, NOW(), NOW(),
(19, '402', 'AVAILABLE', 1100000.00, 'Phòng эксклюзивный с отдельным входом и садом', 3, 8, NOW(), NOW(),
(20, '101', 'AVAILABLE', 350000.00, 'Phòng dostępny для людей с ограниченными возможностями', 4, 9, NOW(), NOW(),
(21, '102', 'AVAILABLE', 400000.00, 'Phòng стандарт с дополнительными поручнями', 4, 1, NOW(), NOW(),
(22, '201', 'AVAILABLE', 450000.00, 'Phòng комфортné с площадью 30 кв.м.', 4, 2, NOW(), NOW(),
(23, '202', 'AVAILABLE', 500000.00, 'Phòng для пар с возможностью установки детской кроватки', 4, 2, NOW(), NOW(),
(24, '301', 'AVAILABLE', 600000.00, 'Phòng тройной с возможностью размещения троих взрослых', 4, 3, NOW(), NOW(),
(25, '101', 'AVAILABLE', 800000.00, 'Phòng эконом-класса у моря с прямой выходидо пляжа', 5, 1, NOW(), NOW(),
(26, '102', 'AVAILABLE', 900000.00, 'Phòng комфортé с видом на море и балконом', 5, 2, NOW(), NOW(),
(27, '201', 'AVAILABLE', 1000000.00, 'Phòng люкс с панорамным видом на океан', 5, 5, NOW(), NOW(),
(28, '202', 'AVAILABLE', 1200000.00, 'Phòng президентский с несколькими комнатами и террасой', 5, 8, NOW(), NOW(),
(29, '301', 'AVAILABLE', 400000.00, 'Phòng доступный у моря с бесплатным лежаком и зонтиком', 5, 9, NOW(), NOW(),
(30, '101', 'AVAILABLE', 450000.00, 'Phòng közvetítői стандарт с вентиляцией и кондиционером', 6, 1, NOW(), NOW(),
(31, '102', 'AVAILABLE', 500000.00, 'Phòng стандарт с видом на сад и palmами', 6, 2, NOW(), NOW(),
(32, '201', 'AVAILABLE', 550000.00, 'Phòng улучшенной планировки с балконом выходящим на море', 6, 2, NOW(), NOW(),
(33, '202', 'AVAILABLE', 600000.00, 'Phоспальь‌شроньелДля семейнogo відпочинку з дитиною', 6, 3, NOW(), NOW(),
(34, '301', 'AVAILABLE', 700000.00, 'Phòng negózii z рецепцией i prywatnym parkingiem', 6, 4, NOW(), NOW(),
(35, '101', 'AVAILABLE', 300000.00, 'Phòng wirtschaft'ль für Reisende mit Budget-Sinn', 7, 1, NOW(), NOW(),
(36, '102', 'AVAILABLE', 350000.00, 'Phòng комфортé с москитными сетками и вентиляцией', 7, 2, NOW(), NOW(),
(37, '201', 'AVAILABLE', 400000.00, 'Phòng для семейного отдыха с детской площадкой во дворе', 7, 3, NOW(), NOW(),
(38, '202', 'AVAILABLE', 450000.00, 'Phòng с выходом террасы непосредственно к реке', 7, 4, NOW(), NOW(),
(39, '301', 'AVAILABLE', 500000.00, 'Phòng повышенной комфортности с кондиционером и вентиляцией', 7, 1, NOW(), NOW(),
(40, '101', 'AVAILABLE', 350000.00, 'Phòng w stylu tropikalnym z drewnianymi meblami i bambusowymi dodatkami', 8, 1, NOW(), NOW(),
(41, '102', 'AVAILABLE', 400000.00, 'Phофіційний номереlor de luxo com varanda e vista para as montanhas', 8, 2, NOW(), NOW(),
(42, '201', 'AVAILABLE', 450000.00, 'Помимо уравновешенности великий персонaл весь спектр гостиничных услуг', 8, 3, NOW(), NOW(),
(43, '202', 'AVAILABLE', 500000.00, 'Номера повышенной комфортности с собственной сауной и джакузи', 8, 4, NOW(), NOW(),
(44, '301', 'AVAILABLE', 550000.00, 'Пентхаус с панорамным видом на город и горы', 8, 8, NOW(), NOW(),
(45, '101', 'AVAILABLE', 250000.00, 'Phòng giá tốt với tiện nghi cơ bản phục vụ du khách và công dân', 9, 1, NOW(), NOW(),
(46, '102', 'AVAILABLE', 300000.00, 'Phòng double sleeper cho gia đình nhỏ hoặc nhóm bạn', 9, 2, NOW(), NOW(),
(47, '201', 'AVAILABLE', 350000.00, 'Phòng triple giường để chứa tối đa 3 người lớn', 9, 3, NOW(), NOW(),
(48, '202', 'AVAILABLE', 400000.00, 'Phòng gia đình với giường lớn và bàn ăn trong phòng', 9, 4, NOW(), NOW(),
(49, '301', 'AVAILABLE', 450000.00, 'Phòng thứgiãn với ban côngniosystem ekstra для релаксации и загара', 9, 5, NOW(), NOW(),
(50, '101', 'AVAILABLE', 400000.00, 'Phòng с панорамным видом на залив и острова', 10, 1, NOW(), NOW(),
(51, '102', 'AVAILABLE', 450000.00, 'Phòng стандарт с видом на гавань и городской пейзаж', 10, 2, NOW(), NOW(),
(52, '201', 'AVAILABLE', 500000.00, 'Phòng улучшенной планировки с панорамным остеклением', 10, 2, NOW(), NOW(),
(53, '202', 'AVAILABLE', 550000.00, 'Phòng комфортé с выходом собственного пляжа и лежаками', 10, 4, NOW(), NOW(),
(54, '301', 'AVAILABLE', 600000.00, 'Phòng sokakсовременый номер с безбарьерным доступом', 10, 5, NOW(), NOW(),
(55, '101', 'AVAILABLE', 200000.00, 'Phòng giá rẻ cho backpacker và sinh viên đi du lịch', 11, 1, NOW(), NOW(),
(56, '102', 'AVAILABLE', 250000.00, 'Phòng standard cho gia đình nhỏ hoặc nhóm bạn 2-3 người', 11, 2, NOW(), NOW(),
(57, '201', 'AVAILABLE', 300000.00, 'Phòng view núi với ban công nhỏ để ngắm paysages dag', 11, 3, NOW(), NOW(),
(58, '202', 'AVAILABLE', 350000.00, 'Phòng Doppelzimmer mit Balkon und schönem Bergblick', 11, 4, NOW(), NOW(),
(59, '301', 'AVAILABLE', 400000.00, 'Phòng con Vista Panorâmica da Serra para ficar de cara para o nascer do sol', 11, 5, NOW(), NOW(),
(60, '101', 'AVAILABLE', 1800000.00, 'Villa có hồ bơi riêng và không gian extremos cho tiệc tùng', 12, 8, NOW(), NOW(),
(61, '102', 'AVAILABLE', 1500000.00, 'Кают-компания с панорамным видом на джунгли и реку', 12, 7, NOW(), NOW(),
(62, '201', 'AVAILABLE', 1200000.00, 'Бунгало с собственным двориком и зоной для барбекю', 12, 6, NOW(), NOW(),
(63, '202', 'AVAILABLE', 900000.00, 'Лодж с панорамным видом на долину и возможностью наблюдения за птицами', 12, 5, NOW(), NOW(),
(64, '301', 'AVAILABLE', 600000.00, 'Phòng chèo đỗ xe và propósitoъекта nel infrastructure', 12, 4, NOW(), NOW(),
(65, '101', 'AVAILABLE', 400000.00, 'Phòng nghỉ trú tại tr cà phê và 침실 공간', 13, 1, NOW(), NOW(),
(66, '102', 'AVAILABLE', 450000.00, 'Phòng nghỉ ngơi tại hầm rượu và espacios de dégustation', 13, 2, NOW(), NOW(),
(67, '201', 'AVAILABLE', 500000.00, 'Каиюродорий специально оформленное для ценителей винного туризма и гастрономии', 13, 3, NOW(), NOW(),
(68, '202', 'AVAILABLE', 550000.00, 'Villa с террасой для наблюдения за закатом и возможностью организации пикника', 13, 4, NOW(), NOW(),
(69, '301', 'AVAILABLE', 600000.00, 'Хостел с отдельными 침대скими местами и общей кухней и зоной отдыха', 13, 5, NOW(), NOW(),
(70, '101', 'AVAILABLE', 2200000.00, 'Phòng để 체크인 4 sao nghỉ dưỡng cực sang trọng với tất cả tiện nghi được đầu tư sb', 14, 15, NOW(), NOW(),
(71, '102', 'AVAILABLE', 2000000.00, 'Phòng trực tiếp bên bờ biển với accès привато и вид на закат точныйтом', 14, 15, NOW(), NOW(),
(72, '201', 'AVAILABLE', 1600000.00, 'Phòng Garden View với khu vườn 따로 và possibilité de préparaton de자기자신입니다', 14, 12, NOW(), NOW(),
(73, '202', 'AVAILABLE', 1400000.00, 'Phòng nội địa view stammtранг с possibilité de установки дополнительных мест', 14, 11, NOW(), NOW(),
(74, '301', 'AVAILABLE', 1000000.00, 'Phòng giao lưu văn hóa và nghỉ dưỡng thuần beloved으로 طراحی شده تا با المنطقة文化交流和放松相结合的场所', 14, 9, NOW(), NOW(),
(75, '101', 'AVAILABLE', 400000.00, 'Phòng cho nghỉ ngơi cuối tuần và công việc remotely trong จังหวัด Sóc Trăng', 15, 1, NOW(), NOW(),
(76, '102', 'AVAILABLE', 450000.00, 'Phòng nghỉ dưỡng cho gia đình và du lịch trong trùi trees và่อง serpentinе', 15, 2, NOW(), NOW(),
(77, '201', 'AVAILABLE', 500000.00, 'Phòng premium cho وفる人とビジネス客にオススメの部屋', 15, 3, NOW(), NOW(),
(78, '202', 'AVAILABLE', 550000.00, 'Phòng nội thất hiện đại và tiện nghi_full_std cho giai đoạnarsh단의租赁期间', 15, 4, NOW(), NOW(),
(79, '301', 'AVAILABLE', 600000.00, 'Phòng suite featuring стильный интерьер и встроенную кухонную зону', 15, 5, NOW(), NOW();

-- Review table
INSERT INTO review (id, rating, title, comment, stay_date, status, user_id, hotel_id, booking_id, created_at, updated_at) VALUES
(1, 5, 'Tuyệt vời', 'Dịch vụ rất tốt, phòng sạch sẽ và đẹp', '2026-08-15', 'APPROVED', 1, 1, 1, NOW(), NOW()),
(2, 4, 'Rất tốt', 'Khách sạn tốt nhưng vị trí hơi xa trung tâm', '2026-08-10', 'APPROVED', 2, 2, 2, NOW(), NOW()),
(3, 3, 'Bình thường', 'Giá cả phù hợp nhưng dịch vụ cần cải thiện', '2026-08-05', 'PENDING', 3, 3, 3, NOW(), NOW()),
(4, 5, 'Xuất sắc', 'Tất cả từ đầu đến cuối đều tốt, sẽ quay lại lần sau', '2026-07-20', 'APPROVED', 4, 4, 4, NOW(), NOW()),
(5, 2, 'Không tốt', 'Có nhiều vấn đề về vệ sinh và noise', '2026-08-12', 'REJECTED', 5, 5, 5, NOW(), NOW()),
(6, 4, 'Khá tốt', 'Dịch vụ tốt nhưng món ăn ở nhà hàng hơi ít lựa chọn', '2026-08-08', 'APPROVED', 6, 6, 6, NOW(), NOW()),
(7, 5, 'Wonderful stay', 'Amazing resort with great beach access and friendly staff', '2026-08-01', 'APPROVED', 7, 7, 7, NOW(), NOW()),
(8, 3, 'Okay experience', 'Standard hotel, nothing special but gets the job done', '2026-07-25', 'PENDING', 8, 8, 8, NOW(), NOW()),
(9, 4, 'Excellent mountain retreat', 'Perfect place for relaxation with beautiful mountain views', '2026-07-18', 'APPROVED', 9, 9, 9, NOW(), NOW(),
(10, 5, 'Beachfront paradise', 'Direct beach access, great food and superb service', '2026-07-15', 'APPROVED', 10, 10, 10, NOW(), NOW(),
(11, 4, 'Good budget option', 'Clean rooms and good service for the price point', '2026-07-12', 'APPROVED', 11, 11, 11, NOW(), NOW(),
(12, 3, 'Needs improvement', 'Good location but facilities could be better maintained', '2026-07-10', 'PENDING', 12, 12, 12, NOW(), NOW(),
(13, 4, 'Coffee lover\'s heaven', 'Great coffee and comfortable rooms perfect for work trips', '2026-07-08', 'APPROVED', 13, 13, 13, NOW(), NOW(),
(14, 5, 'Island luxury', 'Truly exceptional resort experience worth every penny', '2026-07-05', 'APPROVED', 14, 14, 14, NOW(), NOW(),
(15, 4, 'Peaceful riverside stay', 'Quiet and relaxing stay by the river with good amenities', '2026-07-01', 'APPROVED', 15, 15, 15, NOW(), NOW();

-- Booking table
INSERT INTO booking (id, booking_reference, check_in_date, check_out_date, number_of_guests, number_of_nights, total_amount, special_requests, status, customer_id, room_id, created_at, updated_at) VALUES
(1, 'BKREF000001', '2026-08-25', '2026-08-28', 2, 3, 1650000.00, 'Late check-in', 'CONFIRMED', 1, 1, NOW(), NOW()),
(2, 'BKREF000002', '2026-08-30', '2026-09-02', 2, 3, 4800000.00, 'Beachfront room preferred', 'CONFIRMED', 2, 7, NOW(), NOW()),
(3, 'BKREF000003', '2026-09-05', '2026-09-07', 1, 2, 1500000.00, '', 'PENDING', 3, 12, NOW(), NOW()),
(4, 'BKREF000004', '2026-09-10', '2026-09-15', 2, 5, 4500000.00, 'Anniversary celebration - champagne on arrival', 'CONFIRMED', 4, 4, NOW(), NOW()),
(5, 'BKREF000005', '2026-09-20', '2026-09-22', 1, 2, 1200000.00, 'Quiet floor requested', 'CONFIRMED', 5, 5, NOW(), NOW(),
(6, 'BKREF000006', '2026-09-25', '2026-09-28', 2, 3, 1800000.00, 'Extra towels and pillows', 'CONFIRMED', 6, 6, NOW(), NOW(),
(7, 'BKREF000007', '2026-10-01', '2026-10-05', 1, 4, 1400000.00, 'Work desk and good WiFi needed', 'CONFIRMED', 7, 7, NOW(), NOW(),
(8, 'BKREF000008', '2026-10-10', '2026-10-12', 2, 2, 1200000.00, '', 'PENDING', 8, 8, NOW(), NOW(),
(9, 'BKREF000009', '2026-10-15', '2026-10-17', 2, 2, 1400000.00, 'Quiet room for relaxation', 'CONFIRMED', 9, 9, NOW(), NOW(),
(10, 'BKREF000010', '2026-10-20', '2026-10-22', 2, 2, 3200000.00, 'Ocean view suite for honeymoon', 'CONFIRMED', 10, 10, NOW(), NOW(),
(11, 'BKREF000011', '2026-10-25', '2026-10-27', 2, 2, 1000000.00, 'Ground floor room preferred', 'CONFIRMED', 11, 11, NOW(), NOW(),
(12, 'BKREF000012', '2026-11-01', '2026-11-03', 3, 2, 1500000.00, 'Extra bed for child', 'CONFIRMED', 12, 12, NOW(), NOW(),
(13, 'BKREF000013', '2026-11-05', '2026-11-07', 2, 2, 1400000.00, '', 'PENDING', 13, 13, NOW(), NOW(),
(14, 'BKREF000014', '2026-11-10', '2026-11-12', 2, 2, 2800000.00, 'Private transfer from airport', 'CONFIRMED', 14, 14, NOW(), NOW(),
(15, 'BKREF000015', '2026-11-15', '2026-11-17', 1, 2, 1200000.00, 'Late arrival after 10 PM', 'CONFIRMED', 15, 15, NOW(), NOW();

-- Payment table
INSERT INTO payment (id, transaction_id, amount, payment_method, status, paid_at, booking_id, created_at, updated_at) VALUES
(1, 'TXN000001', 1650000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 1, NOW(), NOW()),
(2, 'TXN000002', 4800000.00, 'PAYPAL', 'COMPLETED', NOW(), 2, NOW(), NOW(),
(3, 'TXN000003', 1500000.00, 'BANK_TRANSFER', 'PENDING', NULL, 3, NOW(), NOW(),
(4, 'TXN000004', 4500000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 4, NOW(), NOW(),
(5, 'TXN000005', 1200000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 5, NOW(), NOW(),
(6, 'TXN000006', 1800000.00, 'PAYPAL', 'COMPLETED', NOW(), 6, NOW(), NOW(),
(7, 'TXN000007', 1400000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 7, NOW(), NOW(),
(8, 'TXN000008', 1200000.00, 'CREDIT_CARD', 'PENDING', NULL, 8, NOW(), NOW(),
(9, 'TXN000009', 1400000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 9, NOW(), NOW(),
(10, 'TXN000010', 3200000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 10, NOW(), NOW(),
(11, 'TXN000011', 1000000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 11, NOW(), NOW(),
(12, 'TXN000012', 1500000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 12, NOW(), NOW(),
(13, 'TXN000013', 1400000.00, 'PAYPAL', 'PENDING', NULL, 13, NOW(), NOW(),
(14, 'TXN000014', 2800000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 14, NOW(), NOW(),
(15, 'TXN000015', 1200000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 15, NOW(), NOW();

-- RoomAmenity table (junction table)
INSERT INTO roomamenity (id, room_id, amenity_id, created_at, updated_at) VALUES
-- Room 1 amenities
(1, 1, 1, NOW(), NOW()),   -- Room 1: WiFi
(2, 1, 2, NOW(), NOW()),   -- Room 1: Pool
(3, 1, 4, NOW(), NOW()),   -- Room 1: Restaurant
(4, 1, 7, NOW(), NOW()),   -- Room 1: Parking
(5, 1, 10, NOW(), NOW()),  -- Room 1: TV
(6, 1, 11, NOW(), NOW()),  -- Room 1: AC
(7, 1, 12, NOW(), NOW()),  -- Room 1: Bedding
(8, 1, 14, NOW(), NOW()),  -- Room 1: Balcony

-- Room 2 amenities
(9, 2, 1, NOW(), NOW()),   -- Room 2: WiFi
(10, 2, 2, NOW(), NOW()),  -- Room 2: Pool
(11, 2, 4, NOW(), NOW()),  -- Room 2: Restaurant
(12, 2, 7, NOW(), NOW()),  -- Room 2: Parking
(13, 2, 10, NOW(), NOW()), -- Room 2: TV
(14, 2, 11, NOW(), NOW()), -- Room 2: AC
(15, 2, 12, NOW(), NOW()), -- Room 2: Bedding
(16, 2, 14, NOW(), NOW()), -- Room 2: Balcony

-- Room 3 amenities
(17, 3, 1, NOW(), NOW()),  -- Room 3: WiFi
(18, 3, 2, NOW(), NOW()),  -- Room 3: Pool
(19, 3, 3, NOW(), NOW()),  -- Room 3: Gym
(20, 3, 4, NOW(), NOW()),  -- Room 3: Restaurant
(21, 3, 6, NOW(), NOW()),  -- Room 3: Spa
(22, 3, 7, NOW(), NOW()),  -- Room 3: Parking
(23, 3, 8, NOW(), NOW()),  -- Room 3: Airport Shuttle
(24, 3, 9, NOW(), NOW()),  -- Room 3: Meeting Room
(25, 3, 10, NOW(), NOW()), -- Room 3: TV
(26, 3, 11, NOW(), NOW()), -- Room 3: AC
(27, 3, 12, NOW(), NOW()), -- Room 3: Bedding
(28, 3, 13, NOW(), NOW()), -- Room 3: Minibar
(29, 3, 14, NOW(), NOW()), -- Room 3: Balcony
(30, 3, 15, NOW(), NOW()), -- Room 3: Laundry

-- Room 4 amenities
(31, 4, 1, NOW(), NOW()),  -- Room 4: WiFi
(32, 4, 2, NOW(), NOW()),  -- Room 4: Pool
(33, 4, 3, NOW(), NOW()),  -- Room 4: Gym
(34, 4, 4, NOW(), NOW()),  -- Room 4: Restaurant
(35, 4, 5, NOW(), NOW()),  -- Room 4: Bar
(36, 4, 6, NOW(), NOW()),  -- Room 4: Spa
(37, 4, 7, NOW(), NOW()),  -- Room 4: Parking
(38, 4, 8, NOW(), NOW()),  -- Room 4: Airport Shuttle
(39, 4, 9, NOW(), NOW()),  -- Room 4: Meeting Room
(40, 4, 10, NOW(), NOW()), -- Room 4: TV
(41, 4, 11, NOW(), NOW()), -- Room 4: AC
(42, 4, 12, NOW(), NOW()), -- Room 4: Bedding
(43, 4, 13, NOW(), NOW()), -- Room 4: Minibar
(44, 4, 14, NOW(), NOW()), -- Room 4: Balcony
(45, 4, 15, NOW(), NOW()), -- Room 4: Laundry

-- Room 5 amenities
(46, 5, 1, NOW(), NOW()),   -- Room 5: WiFi
(47, 5, 2, NOW(), NOW()),   -- Room 5: Pool
(48, 5, 4, NOW(), NOW()),   -- Room 5: Restaurant
(49, 5, 7, NOW(), NOW()),   -- Room 5: Parking
(50, 5, 10, NOW(), NOW()),  -- Room 5: TV
(51, 5, 11, NOW(), NOW()),  -- Room 5: AC
(52, 5, 12, NOW(), NOW()),  -- Room 5: Bedding
(53, 5, 14, NOW(), NOW()),  -- Room 5: Balcony

-- Room 6 amenities
(54, 6, 1, NOW(), NOW()),   -- Room 6: WiFi
(55, 6, 2, NOW(), NOW()),   -- Room 6: Pool
(56, 6, 6, NOW(), NOW()),   -- Room 6: Spa
(57, 6, 7, NOW(), NOW()),   -- Room 6: Parking
(58, 6, 8, NOW(), NOW()),   -- Room 6: Airport Shuttle
(59, 6, 10, NOW(), NOW()),  -- Room 6: TV
(60, 6, 11, NOW(), NOW()),  -- Room 6: AC
(61, 6, 12, NOW(), NOW()),  -- Room 6: Bedding
(62, 6, 14, NOW(), NOW()),  -- Room 6: Balcony

-- Room 7 amenities
(63, 7, 1, NOW(), NOW()),   -- Room 7: WiFi
(64, 7, 2, NOW(), NOW()),   -- Room 7: Pool
(65, 7, 3, NOW(), NOW()),   -- Room 7: Gym
(66, 7, 4, NOW(), NOW()),   -- Room 7: Restaurant
(67, 7, 5, NOW(), NOW()),   -- Room 7: Bar
(68, 7, 6, NOW(), NOW()),   -- Room 7: Spa
(69, 7, 7, NOW(), NOW()),   -- Room 7: Parking
(70, 7, 8, NOW(), NOW()),   -- Room 7: Airport Shuttle
(71, 7, 9, NOW(), NOW()),   -- Room 7: Meeting Room
(72, 7, 10, NOW(), NOW()),  -- Room 7: TV
(73, 7, 11, NOW(), NOW()),  -- Room 7: AC
(74, 7, 12, NOW(), NOW()),  -- Room 7: Bedding
(75, 7, 13, NOW(), NOW()),  -- Room 7: Minibar
(76, 7, 14, NOW(), NOW()),  -- Room 7: Balcony
(77, 7, 15, NOW(), NOW()),  -- Room 7: Laundry

-- Room 8 amenities
(78, 8, 1, NOW(), NOW()),   -- Room 8: WiFi
(79, 8, 2, NOW(), NOW()),   -- Room 8: Pool
(80, 8, 3, NOW(), NOW()),   -- Room 8: Gym
(81, 8, 4, NOW(), NOW()),   -- Room 8: Restaurant
(82, 8, 5, NOW(), NOW()),   -- Room 8: Bar
(83, 8, 6, NOW(), NOW()),   -- Room 8: Spa
(84, 8, 10, NOW(), NOW()),  -- Room 8: TV
(85, 8, 11, NOW(), NOW()),  -- Room 8: AC
(86, 8, 12, NOW(), NOW()),  -- Room 8: Bedding
(87, 8, 13, NOW(), NOW()),  -- Room 8: Minibar
(88, 8, 14, NOW(), NOW()),  -- Room 8: Balcony

-- Room 9 amenities
(89, 9, 1, NOW(), NOW()),   -- Room 9: WiFi
(90, 9, 4, NOW(), NOW()),   -- Room 9: Restaurant
(91, 9, 7, NOW(), NOW()),   -- Room 9: Parking
(92, 9, 10, NOW(), NOW()),  -- Room 9: TV
(93, 9, 11, NOW(), NOW()),  -- Room 9: AC
(94, 9, 12, NOW(), NOW()),  -- Room 9: Bedding
(95, 9, 14, NOW(), NOW()),  -- Room 9: Balcony

-- Room 10 amenities
(96, 10, 1, NOW(), NOW()),  -- Room 10: WiFi
(97, 10, 2, NOW(), NOW()),  -- Room 10: Pool
(98, 10, 4, NOW(), NOW()),  -- Room 10: Restaurant
(99, 10, 7, NOW(), NOW()),  -- Room 10: Parking
(100, 10, 10, NOW(), NOW()), -- Room 10: TV
(101, 10, 11, NOW(), NOW()), -- Room 10: AC
(102, 10, 12, NOW(), NOW()), -- Room 10: Bedding
(103, 10, 14, NOW(), NOW()), -- Room 10: Balcony

-- Room 11 amenities
(104, 11, 1, NOW(), NOW()), -- Room 11: WiFi
(105, 11, 2, NOW(), NOW()), -- Room 11: Pool
(106, 11, 3, NOW(), NOW()), -- Room 11: Gym
(107, 11, 4, NOW(), NOW()), -- Room 11: Restaurant
(108, 11, 5, NOW(), NOW()), -- Room 11: Bar
(109, 11, 6, NOW(), NOW()), -- Room 11: Spa
(110, 11, 7, NOW(), NOW()), -- Room 11: Parking
(111, 11, 8, NOW(), NOW()), -- Room 11: Airport Shuttle
(112, 11, 9, NOW(), NOW()), -- Room 11: Meeting Room
(113, 11, 10, NOW(), NOW()), -- Room 11: TV
(114, 11, 11, NOW(), NOW()), -- Room 11: AC
(115, 11, 12, NOW(), NOW()), -- Room 11: Bedding
(116, 11, 13, NOW(), NOW()), -- Room 11: Minibar
(117, 11, 14, NOW(), NOW()), -- Room 11: Balcony
(118, 11, 15, NOW(), NOW()), -- Room 11: Laundry

-- Room 12 amenities
(119, 12, 1, NOW(), NOW()), -- Room 12: WiFi
(120, 12, 2, NOW(), NOW()), -- Room 12: Pool
(121, 12, 4, NOW(), NOW()), -- Room 12: Restaurant
(122, 12, 7, NOW(), NOW()), -- Room 12: Parking
(123, 12, 10, NOW(), NOW()), -- Room 12: TV
(124, 12, 11, NOW(), NOW()), -- Room 12: AC
(125, 12, 12, NOW(), NOW()), -- Room 12: Bedding
(126, 12, 14, NOW(), NOW()), -- Room 12: Balcony

-- Room 13 amenities
(127, 13, 1, NOW(), NOW()), -- Room 13: WiFi
(128, 13, 2, NOW(), NOW()), -- Room 13: Pool
(129, 13, 4, NOW(), NOW()), -- Room 13: Restaurant
(130, 13, 7, NOW(), NOW()), -- Room 13: Parking
(131, 13, 10, NOW(), NOW()), -- Room 13: TV
(132, 13, 11, NOW(), NOW()), -- Room 13: AC
(133, 13, 12, NOW(), NOW()), -- Room 13: Bedding
(134, 13, 14, NOW(), NOW()), -- Room 13: Balcony

-- Room 14 amenities
(135, 14, 1, NOW(), NOW()), -- Room 14: WiFi
(136, 14, 2, NOW(), NOW()), -- Room 14: Pool
(137, 14, 4, NOW(), NOW()), -- Room 14: Restaurant
(138, 14, 7, NOW(), NOW()), -- Room 14: Parking
(139, 14, 10, NOW(), NOW()), -- Room 14: TV
(140, 14, 11, NOW(), NOW()), -- Room 14: AC
(141, 14, 12, NOW(), NOW()), -- Room 14: Bedding
(142, 14, 14, NOW(), NOW()), -- Room 14: Balcony

-- Room 15 amenities
(143, 15, 1, NOW(), NOW()), -- Room 15: WiFi
(144, 15, 2, NOW(), NOW()), -- Room 15: Pool
(145, 15, 4, NOW(), NOW()), -- Room 15: Restaurant
(146, 15, 7, NOW(), NOW()), -- Room 15: Parking
(147, 15, 10, NOW(), NOW()), -- Room 15: TV
(148, 15, 11, NOW(), NOW()), -- Room 15: AC
(149, 15, 12: NOW(), NOW()), -- Room 15: Bedding
(150, 15, 14: NOW(), NOW()), -- Room 15: Balcony