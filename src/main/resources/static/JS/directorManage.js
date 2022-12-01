$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (director, status) {
                $('.directorForm #directorID').val(director.directorID);
                $('.directorForm #directorName').val(director.directorName);

            });
            $('.directorForm #exampleModal').modal();
        }else{
            $('.directorForm #directorID').val('');
            $('.directorForm #directorName').val('');
            $('.directorForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.directorForm #deleteModal #delFilm').attr('href',href);
        $('.directorForm #deleteModal').modal();
    });
});

