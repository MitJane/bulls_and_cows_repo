require: functions.js

require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: common.js
    module = sys.zb-common
    
theme: /

    state: Начало
        q!: $regex</start>
        intent!: /Давай поиграем
        a: Привет! Это игра "Быки и коровы". Я загадаю четырёхзначное число с неповторяющимися цифрами, а ты попытаешься его отгадать. Одно отгаданное число - это "корова". Одно отгаданное число и его верная позиция в загаданном мною числе - это "бык". "Коров" и "быков" может быть несколько. Твоя задача - отгадать всех четырёх "быков". Желаю удачи! Начнём?
        go!: /Начало/Согласен?
        
        state: Согласен?

            state: Да
                intent: /Согласие
                go!: /Игра

            state: Нет
                intent: /Несогласие
                a: Очень жаль. Если передумаешь - скажи "давай поиграем"!
        
    state: Игра
        script:
            if (testMode()) {
                $session.number = "1234";
            }
            else {
                $session.number = numberGenerationFunc();
            }
            $reactions.transition("/Проверка");    

    state: Проверка
        intent: /Число
        script:
            var insertNum = String($parseTree._Number);
            var checkedInsertNum = checkNumber(insertNum);  
            $temp.bulls = 0;
            $temp.cows = 0;
            
            if (checkedInsertNum) {

                for (var index in $session.number) {
                    var myCountPos = checkedInsertNum.indexOf($session.number[index]);
                    if (myCountPos != -1) {
                        myCountPos == index ? $temp.bulls++ : $temp.cows++;
                    }
                }
                if ($temp.bulls == numberLength) {
                    $reactions.answer("Поздравляю, ты выиграл! Хочешь сыграть еще раз?");
                    $reactions.transition("/Начало/Согласен?");
                } else {
                    $reactions.answer("Быков: {{$temp.bulls}}, коров: {{$temp.cows}}");
                }
            
            } else {
                $reactions.answer("Пожалуйста, введи четырёхзначное число с неповторяющимися цифрами!");    
            }

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Пожалуйста, уточните!

