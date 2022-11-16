$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (cinema, status) {
                $('.cinemaForm #cinemaID').val(cinema.cinemaID);
                $('.cinemaForm #cinemaName').val(cinema.cinemaName);
            });
            $('.cinemaForm #exampleModal').modal();
        }
        else{
            $('.cinemaForm #cinemaID').val('');
            $('.cinemaForm #cinemaName').val('');
            $('.cinemaForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.cinemaForm #deleteModal #delFilm').attr('href',href);
        $('.cinemaForm #deleteModal').modal();
    });
});

