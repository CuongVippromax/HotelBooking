-- Sample data for HotelBooking database
-- EXACTLY 15 records for each table as requested

-- Address table (15 records)
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

-- Amenity table (15 records)
INSERT INTO amenity (id, name, description, icon_name, type, created_at, updated_at) VALUES
(1, 'WiFi miễn phí', 'WiFi tốc độ cao miễn phí trong toàn bộ khu vực khách sạn', 'wifi', 'HOTEL_LEVEL', NOW(), NOW()),
(2, 'Bể bơi ngoài trời', 'Bể bơi lớn với view đẹp, thích hợp cho việc thư giãn', 'pool', 'HOTEL_LEVEL', NOW(), NOW()),
(3, 'Phòng gym hiện đại', 'Phòng tập thể dục wyposaż đầy đủ thiết bị hiện đại', 'gym', 'HOTEL_LEVEL', NOW(), NOW()),
(4, 'Nhà hàng trong khách sạn', 'Nhà hàng phục vụ ẩm thực địa phương và quốc tế', 'restaurant', 'HOTEL_LEVEL', NOW(), NOW()),
(5, 'Bar nước uống', 'Bar phục vụ các loại cocktail và đồ uống', 'bar', 'HOTEL_LEVEL', NOW(), NOW()),
(6, 'Spa và dịch vụ massage', 'Dịch vụ spa và massage chuyên nghiệp để thư giãn cơ thể', 'spa', 'HOTEL_LEVEL', NOW(), NOW()),
(7, 'Đỗ xe miễn phí', 'Bãi đỗ xe rộng rãi miễn phí cho khách hàng', 'parking', 'HOTEL_LEVEL', NOW(), NOW()),
(8, 'Dịch vụ đưa đón sân bay', 'Dịch vụ taxi riêng đưa đón từ sân bay tới khách sạn', 'airport-shuttle', 'HOTEL_LEVEL', NOW(), NOW()),
(9, 'Phòng họp và tiệc nghị', 'Phòng họp đầy đủ thiết bị cho công việc và tổ chức sự kiện', 'meeting-room', 'HOTEL_LEVEL', NOW(), NOW()),
(10, 'TiVi màn hình plat', 'TiVi màn hình plat với kênhs quốc tế', 'tv', 'ROOM_LEVEL', NOW(), NOW()),
(11, 'Máy lạnh điều hòa', 'Hệ thống điều hòa không khí với điều khiển nhiệt độ trong phòng', 'ac', 'ROOM_LEVEL', NOW(), NOW()),
(12, 'Bộ chén, chụp giường премиум', 'Bộ chén đỏ và giường ngủ chất lượng để ngủ ngon', 'bedding', 'ROOM_LEVEL', NOW(), NOW()),
(13, 'Mini bar trong phòng', 'Mini bar được оборудован sẵn các loại đồ uống và ăn vặt', 'minibar', 'ROOM_LEVEL', NOW(), NOW()),
(14, 'Ban công riêng', 'Ban công riêng tư với view đẹp cho mỗi phòng', 'balcony', 'ROOM_LEVEL', NOW(), NOW()),
(15, 'Dịch vụ giặt ủi', 'Dịch vụ giặt ủi và khăn быстро та якісно', 'laundry', 'HOTEL_LEVEL', NOW(), NOW());

-- RoomType table (15 records)
INSERT INTO room_type (id, name, description, max_occupancy, bed_type, number_of_beds, size_square_meters, base_price, created_at, updated_at) VALUES
(1, 'Phòng Standard', 'Phòng đơn giản nhưng đủ tiện nghi cần thiết', 2, 'Đơn', 1, 25.0, 500000.00, NOW(), NOW()),
(2, 'Phòng Double', 'Phòng với giường đôi lớn, thích hợp cho cặp đôi', 2, 'Đôi', 1, 30.0, 600000.00, NOW(), NOW()),
(3, 'Phòng Triple', 'Phòng với 3 giường đơn, thích hợp cho gia đình nhỏ', 3, 'Đơn', 3, 35.0, 750000.00, NOW(), NOW()),
(4, 'Phòng Family', 'Phòng gia đình với giường lớn và sofá giường', 4, 'Đôi + Sofá', 2, 45.0, 900000.00, NOW(), NOW()),
(5, 'Phòng Suite', 'Phòng suite sang trọng với phòng khách và ngủ tách rời', 2, 'Đôi', 1, 55.0, 1200000.00, NOW(), NOW()),
(6, 'Phòng Deluxe View', 'Phòng đẹp với view thành phố hoặc biển', 2, 'Đôi', 1, 40.0, 800000.00, NOW(), NOW()),
(7, 'Phòng Gia Đình Junction', 'Phòng spacious với diện tích rộng và tiện nghi sang trọng', 3, 'Đôi + Đơn', 2, 50.0, 1000000.00, NOW(), NOW()),
(8, 'Phòng Penthouse', 'Phòng hàng đầu trên tầng cao nhất với view panoramic', 4, 'Đôi', 2, 80.0, 2000000.00, NOW(), NOW()),
(9, 'Phòng доступный для инвалидов', 'Phòng được thiết kế đặc biệt cho người khuyết tật di chuyển', 2, 'Đơn', 1, 35.0, 550000.00, NOW(), NOW()),
(10, 'Phòng Honeymoon', 'Phòng roмантичecкий với thiết kế đặc sắc cho nguyệt sanctum', 2, 'Đôi', 1, 45.0, 1500000.00, NOW(), NOW()),
(11, 'Phòng Kinh doanh', 'Phòng được thiết kế dành cho khách công việc với zona de lavoro', 1, 'Đơn', 1, 30.0, 700000.00, NOW(), NOW()),
(12, 'Phòng Gia đình lớn', 'Phòng rộng rãi thích hợp cho gia đình có nhiều thành viên', 6, 'Đôi + 2xĐơn', 3, 60.0, 1300000.00, NOW(), NOW()),
(13, 'Phòng Eco Friendly', 'Phòng được thiết kế thân thiện với môi trường, dùng vật liệu tự nhiên', 2, 'Đơn', 1, 30.0, 650000.00, NOW(), NOW()),
(14, 'Phòng cách âm', 'Phòng cách âm đặc biệt để ضمان أقصى حد من الهدوء والسكوت', 2, 'Đôi', 1, 35.0, 700000.00, NOW(), NOW()),
(15, 'Phòng Президентский люкс', 'Phòng sang trọng nhất với tiện nghi 최고 undez gredients duy nhất', 4, 'Đôi + Sofá', 3, 100.0, 3000000.00, NOW(), NOW());

-- User table (15 records)
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

-- Hotel table (15 records)
INSERT INTO hotel (id, name, description, star_rating, email, phone_number, check_in_time, check_out_time, enabled, address_id, created_at, updated_at) VALUES
(1, 'Khách Sạn Hà Nội Center', 'Khách sạn 4 sao ở trung tâm Hà Nội với dịch vụ tốt', 4, 'info@hanocenter.com', '02438765432', '14:00:00', '12:00:00', true, 1, NOW(), NOW()),
(2, 'Khách Sạn Đà Nẵng Beach', 'Khách sạn 5 sao trên bãi biển Đà Nẵng với view tuyệt vời', 5, 'contact@danangbeach.com', '02973876543', '14:00:00', '12:00:00', true, 2, NOW(), NOW()),
(3, 'Khách Sạn TP.HCM Riverside', 'Khách sạn bên sông Sài Gòn với không gian thoáng đãng', 4, 'reservation@tphcmriverside.com', '02363876543', '14:00:00', '12:00:00', true, 3, NOW(), NOW()),
(4, 'Khách Sạn Huế Heritage', 'Khách sạn francia phong cách cổ điển ở trung tâm Huế', 5, 'info@hueheritage.com', '02343876543', '14:00:00', '12:00:00', true, 4, NOW(), NOW()),
(5, 'Khách Sạn Hải Phòng Port', 'Khách sạn cách sian bến decreased và удобства moderna', 3, 'contact@haiphongport.com', '02253876543', '14:00:00', '12:00:00', true, 5, NOW(), NOW()),
(6, 'Khách Sạn Nha Trang Sun', 'Kh nghỉ dưỡng 4 sao trên bãi biển Nha Trang với sport và giải trí', 4, 'reservation@sunshine-natrang.com', '02583876543', '14:00:00', '12:00:00', true, 6, NOW(), NOW()),
(7, 'Khách Sạn Mekong Delta View', 'Khạch sạn ở miền Tây với kiến trúc truyền thống và услуга-гостеприимства', 3, 'info@mekongdelta.com', '02913876543', '14:00:00', '12:00:00', true, 7, NOW(), NOW()),
(8, 'Khách Sạn Đà Lạt Castello', 'Khách sạn phong cách lâu đài ở Đà Lạt với không khí mát mẻ', 4, 'contact@castellodalat.com', '02633876543', '14:00:00', '12:00:00', true, 8, NOW(), NOW()),
(9, 'Khách Sạn Vinh Ngọc Linh', 'Khách sạn 3 sao giá tốt với vị trí đẹp ở Vinh', 3, 'reservation@ngoclinhvinh.com', '02383876543', '14:00:00', '12:00:00', true, 9, NOW(), NOW()),
(10, 'Khách Sạn Quy Nhơn Seaview', 'Khách sạn view biển đẹp ở Quy NHon với bể bơi и центром развлечений', 4, 'info@seaviewquynhon.com', '02563876543', '14:00:00', '12:00:00', true, 10, NOW(), NOW()),
(11, 'Khách Sạn Cao Nguyên Đất Tha', 'Khách sạn ở khu vực cao nguyên với cơ sở vật chất tốt và phòng ngơi yên tĩnh', 3, 'contact@bacbocongo.com', '02163876543', '14:00:00', '12:00:00', true, 11, NOW(), NOW()),
(12, 'Khách Sạn Tây Nguyên Tre', 'Không nghỉ dưỡng trong rừng với các hoạt động phiêu lưu và ecotourism', 4, 'info@trungtainguyen.com', '02603876543', '14:00:00', '12:00:00', true, 12, NOW(), NOW()),
(13, 'Khách Sạn Buôn Ma Thuột Cà Phê', 'Khách sạn đặc trưng với văn hóa cà phê và không gian làm việc', 3, 'reservation@buonmathuot.coffee.com', '02303876543', '14:00:00', '12:00:00', true, 13, NOW(), NOW()),
(14, 'Khách Sạn Phú Quốc Extreme', 'Kh resort 5 sao riêng biệt trên đảo với villas exlosive и сервис экстра-класса', 5, 'contact@phuquocextremo.com', '02973876544', '14:00:00', '12:00:00', true, 14, NOW(), NOW()),
(15, 'Khách Sạn Sóc Trăng Đông Tây', 'Kh khách sạn với view на rzekę и сервіс гостинності найвищого класу', 3, 'info@dongtaysoc.com', '02973876545', '14:00:00', '12:00:00', true, 15, NOW(), NOW());

-- Room table (15 records - 1 per hotel for simplicity)
INSERT INTO room (id, room_number, status, price_per_night, description, hotel_id, room_type_id, created_at, updated_at) VALUES
(1, '101', 'AVAILABLE', 550000.00, 'Phòng Standard ở khách sạn Hà Nội Center', 1, 1, NOW(), NOW()),
(2, '101', 'AVAILABLE', 1800000.00, 'Phòng Luxury Suite ở khách sạn Đà Nẵng Beach', 2, 5, NOW(), NOW()),
(3, '101', 'AVAILABLE', 450000.00, 'Phòng Standard tại Khách sạn TP.HCM Riverside', 3, 1, NOW(), NOW()),
(4, '101', 'AVAILABLE', 350000.00, 'Phòng Standard доступный tại Khách sạn Huế Heritage', 4, 9, NOW(), NOW()),
(5, '101', 'AVAILABLE', 800000.00, 'Phòng Deluxe View 바다 전망 при Khách sạn Hải Phòng Port', 5, 6, NOW(), NOW()),
(6, '101', 'AVAILABLE', 450000.00, 'Phòng Standard при Khách sạn Nha Trang Sun', 6, 1, NOW(), NOW()),
(7, '101', 'AVAILABLE', 300000.00, 'Phòng Economic при Khách sạn Mekong Delta View', 7, 1, NOW(), NOW()),
(8, '101', 'AVAILABLE', 350000.00, 'Phòng Standard уютный при Khách sạn Đà Lạt Castello', 8, 1, NOW(), NOW()),
(9, '101', 'AVAILABLE', 250000.00, 'Phòng giá tốt при Khách sạn Vinh Ngọc Linh', 9, 1, NOW(), NOW()),
(10, '101', 'AVAILABLE', 400000.00, 'Phòng Standard морской ветер при Khách sạn Quy Nhơn Seaview', 10, 1, NOW(), NOW()),
(11, '101', 'AVAILABLE', 200000.00, 'Phòng giá rẻ chois backpacker при Khách sạn Cao Nguyên Đất Tha', 11, 1, NOW(), NOW()),
(12, '101', 'AVAILABLE', 1800000.00, 'Villa могучий берег при Khách sạn Tây Nguyên Tre', 12, 8, NOW(), NOW()),
(13, '101', 'AVAILABLE', 400000.00, 'Phòng cà phê và nghỉ ngơi при Khách sạn Buôn Ma Thuột Cà Phê', 13, 1, NOW(), NOW()),
(14, '101', 'AVAILABLE', 2200000.00, 'Phòng Президентский lux при Khách sạn Phú Quốc Extreme', 14, 15, NOW(), NOW()),
(15, '101', 'AVAILABLE', 400000.00, 'Phòng nghỉ ngơi cuối tuần при Khách sạn Sóc Trăng Đông Tây', 15, 1, NOW(), NOW());

-- Review table (15 records)
INSERT INTO review (id, rating, title, comment, stay_date, status, user_id, hotel_id, booking_id, created_at, updated_at) VALUES
(1, 5, 'Tuyệt vời tại Hà Nội', 'Dịch vụ rất tốt, phòng sạch sẽ và đẹp', '2026-08-15', 'APPROVED', 1, 1, 1, NOW(), NOW()),
(2, 5, 'Điểm view tuyệt vời tại Đà Nẵng', 'Khách sạn tuyệt vời với view biển đẹp', '2026-08-10', 'APPROVED', 2, 2, 2, NOW(), NOW()),
(3, 4, 'Khách sạn tốt tại TP.HCM', 'Khách sạn tốt nhưng vị trí hơi xa trung tâm', '2026-08-05', 'APPROVED', 3, 3, 3, NOW(), NOW()),
(4, 5, 'Khách sạn Heritage Huế', 'Tất cả từ đầu đến cuối đều tốt, sẽ quay lại lần sau', '2026-07-20', 'APPROVED', 4, 4, 4, NOW(), NOW()),
(5, 2, 'Cần改进 tại Hải Phòng', 'Có nhiều vấn đề về vệ sinh và noise', '2026-08-12', 'REJECTED', 5, 5, 5, NOW(), NOW()),
(6, 4, 'Khá thể nghiệm tại Nha Trang', 'Dịch vụ tốt nhưng món ăn ở nhà hàng hơi ít lựa chọn', '2026-08-08', 'APPROVED', 6, 6, 6, NOW(), NOW()),
(7, 5, 'Wonderful Mekong Delta', 'Amazing resort with great views and friendly staff', '2026-08-01', 'APPROVED', 7, 7, 7, NOW(), NOW()),
(8, 3, 'Okay experience tại Đà Lạt', 'Standard hotel, nothing special but gets the job done', '2026-07-25', 'PENDING', 8, 8, 8, NOW(), NOW()),
(9, 4, 'Excellent mountain retreat tại Vinh', 'Perfect place for relaxation with beautiful mountain views', '2026-07-18', 'APPROVED', 9, 9, 9, NOW(), NOW(),
(10, 5, 'Beachfront paradise tại Quy Nhơn', 'Direct beach access, great food and superb service', '2026-07-15', 'APPROVED', 10, 10, 10, NOW(), NOW(),
(11, 4, 'Good budget option tại Cao Nguyên', 'Clean rooms and good service for the price point', '2026-07-12', 'APPROVED', 11, 11, 11, NOW(), NOW(),
(12, 3, 'Needs improvement tại Tây Nguyên', 'Good location but facilities could be better maintained', '2026-07-10', 'PENDING', 12, 12, 12, NOW(), NOW(),
(13, 4, 'Coffee lover\'s heaven tại Buôn Ma Thuột', 'Great coffee and comfortable rooms perfect for work trips', '2026-07-08', 'APPROVED', 13, 13, 13, NOW(), NOW(),
(14, 5, 'Island luxury tại Phú Quốc', 'Truly exceptional resort experience worth every penny', '2026-07-05', 'APPROVED', 14, 14, 14, NOW(), NOW(),
(15, 4, 'Peaceful riverside stay tại Sóc Trăng', 'Quiet and relaxing stay by the river with good amenities', '2026-07-01', 'APPROVED', 15, 15, 15, NOW(), NOW());

-- Booking table (15 records)
INSERT INTO booking (id, booking_reference, check_in_date, check_out_date, number_of_guests, number_of_nights, total_amount, special_requests, status, customer_id, room_id, created_at, updated_at) VALUES
(1, 'BKREF000001', '2026-08-25', '2026-08-28', 2, 3, 1650000.00, 'Late check-in', 'CONFIRMED', 1, 1, NOW(), NOW()),
(2, 'BKREF000002', '2026-08-30', '2026-09-02', 2, 3, 5400000.00, 'Beachfront room preferred', 'CONFIRMED', 2, 2, NOW(), NOW()),
(3, 'BKREF000003', '2026-09-05', '2026-09-07', 1, 2, 900000.00, '', 'PENDING', 3, 3, NOW(), NOW()),
(4, 'BKREF000004', '2026-09-10', '2026-09-15', 2, 5, 1750000.00, 'Anniversary celebration - champagne on arrival', 'CONFIRMED', 4, 4, NOW(), NOW()),
(5, 'BKREF000005', '2026-09-20', '2026-09-22', 1, 2, 1600000.00, 'Quiet floor requested', 'CONFIRMED', 5, 5, NOW(), NOW(),
(6, 'BKREF000006', '2026-09-25'::date, '2026-09-28'::date, 2, 3, 1350000.00, 'Extra towels and pillows', 'CONFIRMED', 6, 6, NOW(), NOW(),
(7, 'BKREF000007', '2026-10-01'::date, '2026-10-05'::date, 1, 4, 1200000.00, 'Work desk and good WiFi needed', 'CONFIRMED', 7, 7, NOW(), NOW(),
(8, 'BKREF000008', '2026-10-10'::date, '2026-10-12'::date, 2, 2, 800000.00, '', 'PENDING', 8, 8, NOW(), NOW(),
(9, 'BKREF000009', '2026-10-15'::date, '2026-10-17'::date, 2, 2, 800000.00, 'Quiet room for relaxation', 'CONFIRMED', 9, 9, NOW(), NOW(),
(10, 'BKREF000010', '2026-10-20'::date, '2026-10-22'::date, 2, 2, 800000.00, 'Ocean view suite for honeymoon', 'CONFIRMED', 10, 10, NOW(), NOW(),
(11, 'BKREF000011', '2026-10-25'::date, '2026-10-27'::date, 2, 2, 400000.00, 'Ground floor room preferred', 'CONFIRMED', 11, 11, NOW(), NOW(),
(12, 'BKREF000012', '2026-11-01'::date, '2026-11-03'::date, 3, 2, 3600000.00, 'Extra bed for child', 'CONFIRMED', 12, 12, NOW(), NOW(),
(13, 'BKREF000013', '2026-11-05'::date, '2026-11-07'::date, 2, 2, 800000.00, '', 'PENDING', 13, 13, NOW(), NOW(),
(14, 'BKREF000014', '2026-11-10'::date, '2026-11-12'::date, 2, 2, 4400000.00, 'Private transfer from airport', 'CONFIRMED', 14, 14, NOW(), NOW(),
(15, 'BKREF000015', '2026-11-15'::date, '2026-11-17'::date, 1, 2, 800000.00, 'Late arrival after 10 PM', 'CONFIRMED', 15, 15, NOW(), NOW());

-- Payment table (15 records)
INSERT INTO payment (id, transaction_id, amount, payment_method, status, paid_at, booking_id, created_at, updated_at) VALUES
(1, 'TXN000001', 1650000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 1, NOW(), NOW()),
(2, 'TXN000002', 5400000.00, 'PAYPAL', 'COMPLETED', NOW(), 2, NOW(), NOW(),
(3, 'TXN000003', 900000.00, 'BANK_TRANSFER', 'PENDING', NULL, 3, NOW(), NOW(),
(4, 'TXN000004', 1750000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 4, NOW(), NOW(),
(5, 'TXN000005', 1600000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 5, NOW(), NOW(),
(6, 'TXN000006', 1350000.00, 'PAYPAL', 'COMPLETED', NOW(), 6, NOW(), NOW(),
(7, 'TXN000007', 1200000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 7, NOW(), NOW(),
(8, 'TXN000008', 800000.00, 'CREDIT_CARD', 'PENDING', NULL, 8, NOW(), NOW(),
(9, 'TXN000009', 800000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 9, NOW(), NOW(),
(10, 'TXN000010', 800000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 10, NOW(), NOW(),
(11, 'TXN000011', 400000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 11, NOW(), NOW(),
(12, 'TXN000012', 3600000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 12, NOW(), NOW(),
(13, 'TXN000013', 800000.00, 'PAYPAL', 'PENDING', NULL, 13, NOW(), NOW(),
(14, 'TXN000014', 4400000.00, 'CREDIT_CARD', 'COMPLETED', NOW(), 14, NOW(), NOW(),
(15, 'TXN000015', 800000.00, 'BANK_TRANSFER', 'COMPLETED', NOW(), 15, NOW(), NOW();

-- RoomAmenity table (junction table - exactly 15 records for simplicity)
-- We'll associate 1 amenity with each of the first 15 rooms
INSERT INTO roomamenity (id, room_id, amenity_id, created_at, updated_at) VALUES
(1, 1, 1, NOW(), NOW()),   -- Room 1: WiFi
(2, 2, 2, NOW(), NOW()),   -- Room 2: Pool
(3, 3, 3, NOW(), NOW()),   -- Room 3: Gym
(4, 4, 4, NOW(), NOW()),   -- Room 4: Restaurant
(5, 5, 5, NOW(), NOW()),   -- Room 5: Bar
(6, 6, 6, NOW(), NOW()),   -- Room 6: Spa
(7, 7, 7, NOW(), NOW()),   -- Room 7: Parking
(8, 8, 8, NOW(), NOW()),   -- Room 8: Airport Shuttle
(9, 9, 9, NOW(), NOW()),   -- Room 9: Meeting Room
(10, 10, 10, NOW(), NOW()), -- Room 10: TV
(11, 11, 11, NOW(), NOW()), -- Room 11: AC
(12, 12, 12, NOW(), NOW()), -- Room 12: Bedding
(13, 13, 13, NOW(), NOW()), -- Room 13: Minibar
(14, 14, 14, NOW(), NOW()), -- Room 14: Balcony
(15, 15, 15, NOW(), NOW()); -- Room 15: Laundry