$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (schedule, status) {
                $('.scheduleForm #scheduleID').val(schedule.scheduleID);
                $('.scheduleForm #showTime').val(schedule.showTime);
                $('.scheduleForm #filmID').val(schedule.filmID);
                $('.scheduleForm #price').val(schedule.price);
                $('.scheduleForm #status').val(schedule.status);
            });
            $('.scheduleForm #exampleModal').modal();
        }
        else{
            $('.scheduleForm #scheduleID').val('');
            $('.scheduleForm #showTime').val('');
            $('.scheduleForm #showTime').val('');
            $('.scheduleForm #price').val('');
            $('.scheduleForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.scheduleForm #deleteModal #delFilm').attr('href',href);
        $('.scheduleForm #deleteModal').modal();
    });
    // $('.scheduleForm #cinema').on('click', function (e) {
    //     e.preventDefault();
    //     var href= $(this).attr('href');
    //     $.get(href, function (rooms, status){
    //         // $('.scheduleForm #room').append("<option th:value='${rooms.roomID}' th:text='${rooms.roomName}'></option>");
    //         var options ='';
    //         for (var i =0 ;i < rooms.length; i ++) {
    //             $('.scheduleForm #room').append($('<option>', {
    //                 value: rooms[i],
    //                 text:  rooms[i]
    //             }));
    //         }
    //     });
});

