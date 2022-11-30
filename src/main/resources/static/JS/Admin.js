
$(document).ready(function (){
    //FIlms
    $('.nBtn, .table .uBtn').on('click', function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();
        if(text =='Update'){
            $.get(href, function(film, status){
                $('.filmForm #filmID').val(film.filmID);
                $('.filmForm #filmName').val(film.filmName);
                $('.filmForm #filmName1').val(film.filmName1);

                $('.filmForm #releaseDate').val(film.releaseDate);
                $('.filmForm #endDate').val(film.endDate);
                $('.filmForm #runtime').val(film.runtime);
                $('.filmForm #rated').val(film.rated);
                $('.filmForm #status').val(film.status);
                $('.filmForm #urlTrailer').val(film.urlTrailer);
                $('.filmForm #filmDescription').val(film.filmDescription);
                $('.filmForm #filmDescription1').val(film.filmDescription1);
                $('.filmForm #director').val(film.director);
                $('.filmForm #producer').val(film.producer);
                $('.filmForm #image').val(film.image);
            });
            $('.filmForm #exampleModal').modal();
        }else{
            $('.filmForm #filmID').val('');
            $('.filmForm #filmName').val('');
            $('.filmForm #filmName1').val('');
            $('.filmForm #image').val('');
            $('.filmForm #releaseDate').val('');
            $('.filmForm #endDate').val('');
            $('.filmForm #runtime').val('');
            $('.filmForm #rated').val('');
            $('.filmForm #status').val('');
            $('.filmForm #urlTrailer').val('');
            $('.filmForm #filmDescription').val('');
            $('.filmForm #filmDescription1').val('');
            $('.filmForm #director').val('');
            $('.filmForm #producer').val('');
            $('.filmForm #exampleModal').modal();
        }
    });
    $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.filmForm #deleteModal #delFilm').attr('href',href);
        $('.filmForm #deleteModal').modal();
    });

    //Schedule
        $('.nBtn, .table .uBtn').on('click', function (e){
            e.preventDefault();
            var href = $(this).attr('href');
            var text = $(this).text();
            if(text == 'Update'){
                $.get(href, function (schedule, status) {
                    $('.scheduleForm #scheduleID').val(schedule.scheduleID);
                    $('.scheduleForm #showTime').val(schedule.showTime);
                });
                $('.scheduleForm #exampleModal').modal();
            }
            else{
                $('.scheduleForm #scheduleID').val();
                $('.scheduleForm #showTime').val();
                $('.scheduleForm #exampleModal').modal();
            }

        });

        $('.table .dBtn').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('.scheduleForm #deleteModal #delFilm').attr('href',href);
        $('.scheduleForm #deleteModal').modal();
    });
    $('#Fileimage').change(function (){
        showImageThumbnail(this);
    });
});
function showImageThumbnail(fileInput){
    file = fileInput.files[0];
    reader= new FileReader();

    reader.onload = function (e){
        $('#thumbnail').attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}