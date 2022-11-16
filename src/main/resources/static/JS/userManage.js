$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (user, status) {
                $('.userForm #userID').val(user.userID);
                $('.userForm #email').val(user.email);
            });
            $('.userForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.userForm #deleteModal #delFilm').attr('href',href);
        $('.userForm #deleteModal').modal();
    });
});

