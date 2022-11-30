var foods = document.querySelector('.foodID');
var combo = document.querySelector('.combo');
var container = document.querySelector('.container');

var foodNumber = 0;
var comboNumber = 0;
var foodID = 0;
var comboID = 0;

var filmID= $('.filmID').attr('value')
var idRoom = $('.room').attr('value');
var idDate =$('.date').attr('value');
var idTime =$('.time').attr('value');
var total = $('.total').attr('value');
var listArray =[];
var idChair = 0

var foodArray= [];
var comboArray= [];

var foodAmount = [];
var comboAmount = [];

//List seat
$(document).ready(function () {
    $('.seat').each(function () {
        idChair = $(this).attr('value');
        listArray.push(idChair);
    });

});
$(document).ready(function (){
    $('.food-number').change(function (){
        foodID = $(this).attr('foodID');
        var found = $.inArray(foodID,foodArray);
        foodNumber= $(this).val();
        if(found >=0){
            foodArray.splice(found,1);
            foodAmount.splice(found,1);
        }else{
            foodArray.push(foodID);
            foodAmount.push(foodNumber);
        }
        console.log(foodID);
        console.log(foodNumber);
    });
    $('.combo-number').change(function (){
        comboID = $(this).attr('comboID');
        comboNumber= $(this).val();
        var found = $.inArray(comboID,comboArray);
        if(found >=0){
            comboArray.splice(found,1);
            comboAmount.splice(found,1);
        }else{
            comboArray.push(comboID);
            comboAmount.push(comboNumber);
        }
        console.log(comboID);
        console.log(comboAmount);
    });
});

$('#btnPayment').click(()=>{
 if(comboArray !== null && comboArray !== null) {
     window.location.href = '/Payment/viewPrice?filmID=' + filmID + '&date=' + idDate + '&time=' + idTime + '&room=' + idRoom + '&seats=' + listArray + '&foods=' + foodArray + '&foodAmount=' + foodAmount + '&combos=' + comboArray + '&comboAmount=' + comboAmount + '&total=' + total;
 }
});
