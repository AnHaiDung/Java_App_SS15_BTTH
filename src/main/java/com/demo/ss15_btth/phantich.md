1. I/O 
   - Tính năng 1: Lọc Marketing 
    + Input: Tên danh mục (category) và trạng thái kinh doanh (status = true). 
    + Output: Danh sách các sản phẩm đang kinh doanh thuộc danh mục đó. 
   - Tính năng 2: Phân trang & Sắp xếp 
     + Input: Số trang (page) và kích thước trang (size). 
     + Output: Đối tượng Page chứa danh sách sản phẩm được sắp xếp theo giá giảm dần (từ cao đến thấp). 
   - Tính năng 3: Mobile API
     + Input: ID sản phẩm hoặc yêu cầu lấy danh sách. 
     + Output: DTO rút gọn (Projection) chỉ gồm hai trường thông tin là tên sản phẩm và giá. 
   - Tính năng 4: Tìm kiếm động 
     + Input: Các tiêu chí tìm kiếm không bắt buộc như từ khóa tên, khoảng giá tối thiểu, khoảng giá tối đa. 
     + Output: Danh sách sản phẩm khớp với tất cả các tiêu chí người dùng đã nhập.
2. So sánh
   - Sử dụng Method Query:
     + Ưu điểm: Triển khai cực nhanh, Spring Data JPA tự hiểu logic qua tên hàm, giảm thiểu sai sót cú pháp SQL. 
     + Nhược điểm: Tên hàm sẽ trở nên rất dài và khó đọc nếu cần kết hợp nhiều tiêu chí sắp xếp phức tạp. 
   - Sử dụng @Query (JPQL/Native):
     + Ưu điểm: Cho phép viết câu lệnh truy vấn tường minh, tối ưu hóa được các cột cần lấy (SELECT), xử lý tốt các logic phức tạp mà tên hàm không thể hiện hết được. 
     + Nhược điểm: Đòi hỏi người viết phải nắm vững cú pháp JPQL hoặc SQL, dễ lỗi nếu viết sai chuỗi truy vấn.
Lựa chọn: Ưu tiên @Query cho tính năng 2 để đảm bảo câu lệnh SQL sinh ra là tối ưu nhất cho việc sắp xếp giá.
3. luồng xử lý
   - Xử lý bẫy lỗi logic giá:
     + Trước khi gọi xuống Repository, hệ thống sẽ so sánh minPrice và maxPrice. 
     + Nếu người dùng nhập minPrice lớn hơn maxPrice, Service sẽ ném ra một ngoại lệ (RuntimeException) kèm thông báo lỗi cụ thể để yêu cầu người dùng nhập lại thay vì trả về kết quả rỗng.
   - Xử lý bẫy lỗi phân trang:
     + Hệ thống kiểm tra giá trị page được truyền từ Client. 
     + Nếu page < 0, hệ thống sẽ tự động gán về giá trị 0 hoặc chặn lại ngay tại tầng Service để tránh gây lỗi truy vấn cho Database.
   - Tối ưu hóa dữ liệu Mobile:
     + Thay vì dùng SELECT *, hệ thống sử dụng Interface Projection để ép Hibernate chỉ sinh ra câu lệnh lấy đúng 2 cột dữ liệu. 
     + Điều này giúp tiết kiệm tài nguyên hệ thống và cải thiện tốc độ phản hồi cho ứng dụng di động.