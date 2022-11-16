$(document).ready(function () {
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text == 'Update'){
            $.get(href, function (voucher, status) {
                $('.voucherForm #id').val(voucher.voucherID);
                $('.voucherForm #voucherID').val(voucher.voucherID);
                $('.voucherForm #voucherName').val(voucher.voucherName);
                $('.voucherForm #voucherName1').val(voucher.voucherName1);
                $('.voucherForm #amount').val(voucher.amount);
                $('.voucherForm #discount').val(voucher.discount);
                $('.voucherForm #effectiveDate').val(voucher.effectiveDate);
                $('.voucherForm #expireDate').val(voucher.expireDate);
            });
            $('.voucherForm #exampleModal').modal();
        }
        else{
            $('.voucherForm #voucherID').val('');
            $('.voucherForm #voucherName').val('');
            $('.voucherForm #voucherName1').val('');
            $('.voucherForm #amount').val('');
            $('.voucherForm #discount').val('');
            $('.voucherForm #effectiveDate').val('');
            $('.voucherForm #expireDate').val('');
            $('.voucherForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.voucherForm #deleteModal #delFilm').attr('href',href);
        $('.voucherForm #deleteModal').modal();
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

