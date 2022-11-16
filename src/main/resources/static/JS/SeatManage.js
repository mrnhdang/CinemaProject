const container = document.querySelector('.container-fluid');
const seats = document.querySelectorAll('.row.seat:not(.occupied)');


//show the user to see the money of chairs


// use to push array of chair
var idChair = 0;
var listArray = [];

//get id from schedule page
var filmID= $('.filmID').attr('value')
var idCinema = $('.cinema').attr('value');
var idDate =$('.date').attr('value');
var idTime =$('.time').attr('value');
var price = 0;

var roomID = $('.room-info').attr('value')

// save selected movie index and price
function setMovieData(movieIndex, moviePrice){
    localStorage.setItem('selectedMovieIndex',movieIndex);
    localStorage.setItem('selectedMoviePrice',moviePrice);
}


//update total and count
// function updateSelectedCount(){
//     const selectedSeats = document.querySelectorAll('.row .seat.selected');
//
//     const seatsIndex =[...selectedSeats].map(function(seat){
//         return [...seats].indexOf(seat);
//     });
//     localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));
//
//     const selectedSeatsCount = selectedSeats.length;
//
//     count.innerText=selectedSeatsCount;
//     total.innerText=selectedSeatsCount * ticketPrice;
//     price = selectedSeatsCount *ticketPrice;
// }
// get data from localStorage and populate the ui
function populateUI(){
    const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));

    if(selectedSeats !== null && selectedSeats.length >0){
        seats.forEach((seat,index)=>{
            if(selectedSeats.indexOf(index) > -1){
                seat.classList.add('selected');
            }
        });
    }
    const selectedMovieIndex = localStorage.getItem('selectedMovieIndex');

    if(selectedMovieIndex != null){
        movieSelect.selectedIndex = selectedMovieIndex;
    }
}

//movie select event
// movieSelect.addEventListener('change', e=> {
//     ticketPrice = +e.target.value;
//
//     setMovieData(e.target.selectedIndex, e.target.value);
//
//     updateSelectedCount();
// });

container.addEventListener('click', (e)=> {
    if (e.target.classList.contains('seat') && !e.target.classList.contains('occupied')) {
        e.target.classList.toggle('selected');
    }
});
populateUI();
$('.seat').click(function () {
    idChair = $(this ).attr('value');
    var found = $.inArray(idChair,listArray);
    if(found >=0){
        listArray.splice(found,1);
    }else{
        listArray.push(idChair);
    }
    console.log(listArray);
});
$('.uBtn').click(function (){
    window.location.href="/Admin/seat/updateSelect?list="+listArray+"&id="+roomID;
})

// function removeChair() {
//     var sportId = idChair;
//
//     $.ajax({
//         method : "DELETE",
//         url : "/Admin/seat/delete/" + sportId,
//         success: function (result) {
//             console.log(result);
//         },
//         error: function (e) {
//             console.log(e);
//         }
//     })
//
// }
// $('.seat').click(function () {
//     idChair = $(this).attr('value');
//     listArray.push(idChair);
// });
//
// $('#btnPayment').click((e)=>{
//     if(listArray !== null && idChair !== 0){
//         window.location.href = '/Films/Food?filmID='+filmID+'&date='+idDate+'&time='+idTime+'&cinema='+idCinema+'&seats='+listArray+'&total='+price;
//     }
//     else{
//         alert("Vui long chon day du");
//         e.preventDefault();
//     }
//
// });