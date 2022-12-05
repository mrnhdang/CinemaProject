$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (news, status) {
                $('.newForm #newID').val(news.newID);
                $('.newForm #newName').val(news.newName);
                $('.newForm #newName1').val(news.newName1);
                $('.newForm #newTitle').val(news.newTitle);
                $('.newForm #newTitle1').val(news.newTitle);
                $('.newForm #image').val(news.image);

            });
            $('.newForm #exampleModal').modal();
        }
        else{
            $('.newForm #newID').val('');
            $('.newForm #newName').val('');
            $('.newForm #newName1').val('');
            $('.newForm #image').val('');
            $('.newForm #newTitle').val(news.newTitle);
            $('.newForm #newTitle1').val(news.newTitle);
            $('.newForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.newForm #deleteModal #delFilm').attr('href',href);
        $('.newForm #deleteModal').modal();
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

