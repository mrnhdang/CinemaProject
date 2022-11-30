const container = document.querySelector('.most-popular');

var filmID =$('.movie').attr('value');

var idRoom = 0;
var idSchedule = 0;
var idDate = 0;
var idTime = 0;

container.addEventListener('click', (e)=>{
    if(e.target.classList.contains('div') &&  !e.target.classList.contains('occupied')){
        e.target.classList.toggle('selected');
    }


});
// $('.card-schedule').click(function(){
//     idSchedule = $(this).attr('value');
// });
// $('.card-cinema').click(function(){
//     idCinema = $(this).attr('value');
// });
$('.card-time').click(function(){
    idTime = $(this).attr('value');
    idDate = $(this).attr('date');
    idRoom =$(this).attr('room');
});
$('#btnSeat').click((e)=>{
    if(idRoom !==0 && idDate !==0 && idTime !== 0){
        window.location.href = '/Films/Seat?filmID='+filmID+'&date='+idDate+'&time='+idTime+'&room='+idRoom;
    }
    else{
        alert("Vui long chon day du");
        e.preventDefault();
    }


});
