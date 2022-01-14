/*
*генерация числа с 4 неповторяющимися цифрами
*/

var numberLength = 4

function numberGenerationFunc()
{
    var generatedNum = "";
    var i = 0;
        while (i < numberLength) {
            var number = Math.floor(Math.random() * 9) + 1;
            if (generatedNum.indexOf(number) == -1) {
                 generatedNum += number; 
                 i++;
            }
        }
    return generatedNum; 
}

/*
*проверка на соответствие введенного числа
*4 числовым символам с помощью регулярного выражения
*и проверка на отсутствие повторяющихся символов(в цикле)
*/

function checkNumber(number) {
    var regex = /^([0-9]{4})$/gim;
    if (regex.test(number)) {
        for (var i = 0; i < numberLength; i++) {
            if (number.indexOf(number[i], i + 1) != -1) {
                return false;
            }    
        } 
        return number;
    } else {
        return false;  
    }
}
