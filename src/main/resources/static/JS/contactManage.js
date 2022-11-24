$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (contact, status) {
                $('.contactForm #contactID').val(contact.contactID);
                $('.contactForm #contactName').val(contact.contactName);
                $('.contactForm #contactName1').val(contact.contactName1);

            });
            $('.contactForm #exampleModal').modal();
        }else{
            $('.contactForm #contactID').val('');
            $('.contactForm #contactName').val('');
            $('.contactForm #contactName1').val('');
            $('.contactForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.contactForm #deleteModal #delFilm').attr('href',href);
        $('.contactForm #deleteModal').modal();
    });
});

