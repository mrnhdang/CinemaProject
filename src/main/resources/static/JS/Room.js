$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (room, status) {
                $('.roomForm #roomID').val(room.roomID);
                $('.roomForm #roomName').val(room.roomName);
            });
            $('.roomForm #exampleModal').modal();
        }
        else{
            $('.roomForm #roomID').val('');
            $('.roomForm #roomName').val('');
            $('.roomForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.roomForm #deleteModal #delFilm').attr('href',href);
        $('.roomForm #deleteModal').modal();
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

