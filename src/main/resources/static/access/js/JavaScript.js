document.addEventListener("DOMContentLoaded", function () {
    let currentIndex = 0;
    const slides = document.querySelectorAll('.slide');
    const totalSlides = slides.length;

    // Kiểm tra nếu có slide thì mới chạy
    if (totalSlides === 0) {
        console.warn("Không tìm thấy slide nào trên trang này.");
        return;
    }

    // Hàm hiển thị slide
    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === index);
        });
    }

    // Tự động chuyển slide mỗi 7 giây
    const slideInterval = setInterval(() => {
        currentIndex = (currentIndex + 1) % totalSlides;
        showSlide(currentIndex);
    }, 7000);

    // Hiển thị slide đầu tiên khi tải trang
    showSlide(currentIndex);

    // Xử lý nút chuyển slide
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');

    if (prevButton) {
        prevButton.addEventListener('click', () => {
            clearInterval(slideInterval); // Dừng auto-play khi người dùng bấm nút
            currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
            showSlide(currentIndex);
        });
    } else {
        console.warn("Không tìm thấy nút 'prev' trên trang này.");
    }

    if (nextButton) {
        nextButton.addEventListener('click', () => {
            clearInterval(slideInterval); // Dừng auto-play khi người dùng bấm nút
            currentIndex = (currentIndex + 1) % totalSlides;
            showSlide(currentIndex);
        });
    } else {
        console.warn("Không tìm thấy nút 'next' trên trang này.");
    }
});


//Tag product
document.addEventListener("DOMContentLoaded", function () {
    const filters = document.querySelectorAll(".product-menu-filter");
    const items = document.querySelectorAll(".product-menu-item");

    filters.forEach(filter => {
        filter.addEventListener("click", function () {
            // Xóa class active khỏi tất cả filter
            filters.forEach(f => f.classList.remove("active"));
            this.classList.add("active");

            // Lấy giá trị của filter được chọn
            const selectedFilter = this.getAttribute("data-filter");

            // Hiển thị / Ẩn món ăn theo tag
            items.forEach(item => {
                if (selectedFilter === "all") {
                    item.classList.remove("hide");
                } else {
                    if (item.classList.contains(selectedFilter)) {
                        item.classList.remove("hide");
                    } else {
                        item.classList.add("hide");
                    }
                }
            });
        });
    });
});

//số lương khác hàng
function changeGuests(value) {
    const guestCountElement = document.getElementById("guest-count");
    let currentGuests = parseInt(guestCountElement.textContent);

    // Giới hạn số khách tối thiểu là 1
    currentGuests += value;
    if (currentGuests < 1) {
        currentGuests = 1;
    }

    // Cập nhật số lượng khách hiển thị
    guestCountElement.textContent = currentGuests;
}


//lịch
document.addEventListener("DOMContentLoaded", function () {
    const calendarDates = document.getElementById("calendar-dates");
    const calendarMonth = document.getElementById("calendar-month");
    const prevMonthBtn = document.getElementById("prev-month");
    const nextMonthBtn = document.getElementById("next-month");

    // Kiểm tra nếu không có lịch trên trang → Không chạy code lịch
    if (!calendarDates || !calendarMonth) {
        console.warn("Không tìm thấy lịch trên trang, bỏ qua việc renderCalendar().");
        return;
    }

    const today = new Date();
    let selectedDate = null;
    let selectedDay = null; // Lưu ngày đã chọn
    let currentMonth = today.getMonth();
    let currentYear = today.getFullYear();

    const renderCalendar = () => {
        const firstDay = new Date(currentYear, currentMonth, 1).getDay();
        const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate();

        // Cập nhật tháng trên giao diện
        calendarMonth.textContent = `${new Intl.DateTimeFormat('en', { month: 'long' }).format(new Date(currentYear, currentMonth))} ${currentYear}`;

        calendarDates.innerHTML = "";

        // Tạo khoảng trống trước ngày 1 để đúng cột
        for (let i = 0; i < firstDay; i++) {
            const emptyDiv = document.createElement("div");
            emptyDiv.classList.add("calendar-date", "empty");
            calendarDates.appendChild(emptyDiv);
        }

        // Tạo ngày trong tháng
        for (let day = 1; day <= lastDate; day++) {
            const dateDiv = document.createElement("div");
            dateDiv.classList.add("calendar-date");
            dateDiv.textContent = day;

            // Đánh dấu ngày hiện tại
            if (day === today.getDate() && currentMonth === today.getMonth() && currentYear === today.getFullYear()) {
                dateDiv.classList.add("today");
            }

            // Nếu ngày này đã được chọn trước đó, đánh dấu lại
            if (selectedDay && selectedDay.day === day && selectedDay.month === currentMonth && selectedDay.year === currentYear) {
                dateDiv.classList.add("selected");
                selectedDate = dateDiv;
            }

            // Xử lý chọn ngày
            dateDiv.addEventListener("click", () => {
                if (selectedDate) {
                    selectedDate.classList.remove("selected");
                }
                dateDiv.classList.add("selected");
                selectedDate = dateDiv;
                selectedDay = { day, month: currentMonth, year: currentYear };
            });

            calendarDates.appendChild(dateDiv);
        }
    };

    // Chuyển tháng
    const prevMonth = () => {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        renderCalendar();
    };

    const nextMonth = () => {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        renderCalendar();
    };

    // Gán sự kiện cho nút chuyển tháng (nếu có)
    if (prevMonthBtn) {
        prevMonthBtn.addEventListener("click", prevMonth);
    }
    if (nextMonthBtn) {
        nextMonthBtn.addEventListener("click", nextMonth);
    }

    // Khởi tạo lịch khi tải trang
    renderCalendar();
});


//=================== BUTTON TIME SLOTS ===================//
const timeSlotsContainer = document.getElementById("time-slots");

// Kiểm tra nếu phần tử tồn tại trước khi thêm nút
if (timeSlotsContainer) {
    for (let hour = 10; hour <= 23; hour++) {
        const timeSlot = document.createElement("button");
        timeSlot.classList.add("time-slot");
        timeSlot.textContent = formatTime(hour);

        timeSlot.addEventListener("click", () => {
            document.querySelectorAll(".time-slot").forEach(slot => slot.classList.remove("selected"));
            timeSlot.classList.add("selected");
        });

        timeSlotsContainer.appendChild(timeSlot);
    }
} else {
    console.warn("Không tìm thấy phần tử 'time-slots' trên trang, bỏ qua việc tạo time slots.");
}


// ================== GO TO STEP FUNCTION ================== //
function goToStep(stepNumber) {
    // Ẩn tất cả các bước
    document.querySelectorAll(".table-step-content").forEach(content => content.classList.remove("active"));
    document.querySelectorAll(".table-step").forEach(step => step.classList.remove("active"));

    // Kích hoạt bước được chọn
    document.querySelector(`.table-step[data-step="${stepNumber}"]`).classList.add("active");
    document.querySelector(`.table-step-content.table-step-${stepNumber}`).classList.add("active");

    // Nếu quay lại Bước 1, reset thông tin đã chọn
    if (stepNumber === 1) {
        selectedDate = null;
        document.querySelectorAll(".calendar-date.selected").forEach(date => date.classList.remove("selected"));
        document.querySelectorAll(".time-slot.selected").forEach(slot => slot.classList.remove("selected"));
    }

    // Nếu chuyển sang Bước 2, kiểm tra và đặt giá trị mặc định nếu cần
    if (stepNumber === 2) {
        let selectedTimeSlot = document.querySelector(".time-slot.selected");

        // Nếu chưa chọn ngày, đặt mặc định là ngày hiện tại
        if (!selectedDay) {
            const today = new Date();
            selectedDay = {
                day: today.getDate(),
                month: today.getMonth(),
                year: today.getFullYear()
            };
            console.log(`Chưa chọn ngày. Mặc định chọn ngày hiện tại: ${selectedDay.day}/${selectedDay.month + 1}/${selectedDay.year}`);
        }

        // Nếu chưa chọn thời gian, đặt mặc định là 10:00
        if (!selectedTimeSlot) {
            const defaultTime = "10:00";
            let timeSlotsContainer = document.getElementById("time-slots");
            
            if (timeSlotsContainer) {
                let defaultTimeElement = Array.from(timeSlotsContainer.children).find(slot => slot.textContent.trim() === defaultTime);

                // Nếu có sẵn slot 10:00, chọn nó
                if (defaultTimeElement) {
                    defaultTimeElement.classList.add("selected");
                    selectedTimeSlot = defaultTimeElement;
                } else {
                    // Nếu không có sẵn, tạo một slot mới
                    defaultTimeElement = document.createElement("div");
                    defaultTimeElement.classList.add("time-slot", "selected");
                    defaultTimeElement.textContent = defaultTime;
                    timeSlotsContainer.appendChild(defaultTimeElement);
                    selectedTimeSlot = defaultTimeElement;
                }
                console.log("Chưa chọn thời gian. Mặc định chọn 10:00");
            }
        }

        // Cập nhật thông tin đặt bàn
        document.getElementById("summary-guests").textContent = document.getElementById("guest-count").textContent;
        document.getElementById("summary-date").textContent =
            `${new Intl.DateTimeFormat('en', { month: 'long' }).format(new Date(selectedDay.year, selectedDay.month))} ${selectedDay.day}, ${selectedDay.year}`;
        document.getElementById("summary-time").textContent = selectedTimeSlot.textContent;
    }

    // Nếu chuyển sang Bước 3, kiểm tra thông tin khách hàng
    if (stepNumber === 3) {
        const firstName = document.getElementById("first-name").value.trim();
        const lastName = document.getElementById("last-name").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const message = document.getElementById("message").value.trim();
        const termsChecked = document.getElementById("terms").checked;

        if (!firstName || !lastName || !email || !phone || !termsChecked) {
            alert("Vui lòng điền đầy đủ thông tin trước khi tiếp tục!");
            return;
        }

        // Cập nhật thông tin xác nhận
        document.getElementById("confirm-guests").textContent = document.getElementById("summary-guests").textContent;
        document.getElementById("confirm-date").textContent = document.getElementById("summary-date").textContent;
        document.getElementById("confirm-time").textContent = document.getElementById("summary-time").textContent;
        document.getElementById("confirm-name").textContent = firstName + " " + lastName;
        document.getElementById("confirm-email").textContent = email;
        document.getElementById("confirm-phone").textContent = phone;

        // Hiển thị tin nhắn trong Bước 3
        const confirmMessageElement = document.getElementById("confirm-message");
        if (confirmMessageElement) {
            confirmMessageElement.textContent = message || "Không có tin nhắn.";
        } else {
            console.warn("Không tìm thấy thẻ hiển thị tin nhắn ở Bước 3. Hãy kiểm tra lại HTML!");
        }
    }
}

// ================== EVENT LISTENER CHO NÚT "BOOK A TABLE" (BƯỚC 1 -> BƯỚC 2) ================== //
document.addEventListener("DOMContentLoaded", function () {
    const bookButtonStep1 = document.querySelector(".table-book-btn-step-1");
    if (bookButtonStep1) {
        bookButtonStep1.addEventListener("click", function () {
            goToStep(2);
        });
    } else {
        console.error("Không tìm thấy nút 'BOOK A TABLE'. Kiểm tra lại HTML!");
    }
});

// ================== EVENT LISTENER CHO NÚT "CHECKOUT" (BƯỚC 2 -> BƯỚC 3) ================== //
document.addEventListener("DOMContentLoaded", function () {
    const bookingForm = document.getElementById("booking-form");

    if (bookingForm) {
        bookingForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn form gửi dữ liệu đi và reload trang
            goToStep(3); // Chuyển sang bước 3
        });
    } else {
        console.error("Không tìm thấy form 'booking-form'. Kiểm tra lại HTML!");
    }
});


// ================== EVENT LISTENER CHO NÚT "BACK TO BROWSE" (QUAY LẠI BƯỚC 1) ================== //
document.addEventListener("DOMContentLoaded", function () {
    const backButton = document.querySelector(".table-back-btn");
    if (backButton) {
        backButton.addEventListener("click", function () {
            goToStep(1);
        });
    } else {
        console.error("Không tìm thấy nút 'Back to Browse'. Kiểm tra lại HTML!");
    }
});

// ================== EVENT LISTENER CHO NÚT "BACK TO DETAILS" (QUAY LẠI BƯỚC 2) ================== //
document.addEventListener("DOMContentLoaded", function () {
    const backToDetailsButton = document.querySelector(".table-back-btn-to-details");
    if (backToDetailsButton) {
        backToDetailsButton.addEventListener("click", function () {
            goToStep(2);
        });
    } else {
        console.error("Không tìm thấy nút 'Back to Details'. Kiểm tra lại HTML!");
    }
});

//button phát video
document.addEventListener("DOMContentLoaded", function () {
    const videoContainer = document.querySelector(".about-video");
    const video = document.querySelector(".about-video-content");
    const playButton = document.querySelector(".about-video-play");

    playButton.addEventListener("click", function () {
        if (video.paused) {
            video.play();
            playButton.style.display = "none";
            videoContainer.classList.add("playing"); // Thêm class để đổi background của about-video
        }
    });

    video.addEventListener("pause", function () {
        playButton.style.display = "block";
        videoContainer.classList.remove("playing"); // Trả lại màu nền cũ khi dừng video
    });

    video.addEventListener("play", function () {
        playButton.style.display = "none";
        videoContainer.classList.add("playing");
    });

    video.addEventListener("ended", function () {
        playButton.style.display = "block";
        videoContainer.classList.remove("playing");
    });
});

