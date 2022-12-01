$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (producer, status) {
                $('.producerForm #producerID').val(producer.producerID);
                $('.producerForm #producerName').val(producer.producerName);

            });
            $('.producerForm #exampleModal').modal();
        }else{
            $('.producerForm #producerID').val('');
            $('.producerForm #producerName').val('');
            $('.producerForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.producerForm #deleteModal #delFilm').attr('href',href);
        $('.producerForm #deleteModal').modal();
    });
});

