$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (address, status) {
                $('.addressForm #addressID').val(address.addressID);
                $('.addressForm #addressName').val(address.addressName);
                $('.addressForm #addressName1').val(address.addressName1);

            });
            $('.addressForm #exampleModal').modal();
        }else{
            $('.addressForm #addressID').val('');
            $('.addressForm #addressName').val('');
            $('.addressForm #addressName1').val('');
            $('.addressForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.addressForm #deleteModal #delFilm').attr('href',href);
        $('.addressForm #deleteModal').modal();
    });
});

