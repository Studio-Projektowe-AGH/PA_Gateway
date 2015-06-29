angular.module('GeneratorModule', [])
.factory('GeneratorService', ['$rootScope', function($rootScope){
     var service = {};
        service.getNowDateStamp = getNowDateStamp;
        service.getNowTimeStamp = getNowTimeStamp;
        service.getNowDateAndTime = getNowDateAndTime;
        service.getArrayOfDays = genArrayOfDays;
        service.getArrayOfMonths = genArrayOfMonts;

        return service;

        function getNowDateStamp(){
            var today = new Date();
            var date = [today.getFullYear(), today.getMonth() + 1, today.getDay()];
            for (var i = 0; i < 3; i++) {
                if (date[i] < 10) {
                    date[i] = "0" + date[i];
                }
            }

            return date[0] + "-" + date[1] + "-" + date[2];
        }
        function getNowTimeStamp(){
            var today = new Date();
            var time = [today.getHours(), today.getMinutes()];
            for (var i = 0; i < 2; i++) {
                if (time[i] < 10) {
                    time[i] = "0" + time[i];
                }
            }

            return time[0] + ":" + time[1];
        }
        function getNowDateAndTime(){
            var date = getNowDateStamp();
            var time = getNowTimeStamp();

            return date + " " + time;
        }

        function genArrayOfDays(dBegin, dEnd){
            var dateBegin = genDayArray(dBegin);
            var dateEnd = genDayArray(dEnd);

            var index = 0;
        //    znajdz pierwszy element tablicy rozny od siebie i zapisz indeks
           for(var i = 0; i < dateBegin.length; i++){
               if(dateBegin[i] != dateEnd[i]){
                   index = i;
                   break;
               }
           }

        }

        function genArrayOfMonts(dBegin, dEnd){

        }

        function genDayArray(date){
        //data w foramcie YYYY-MM-DD albo YYYY-MM-DD GG:Mn
            var dateArr = date.split('-');

            var year = dateArr[0];
            var month = dateArr[1];
            var tmp = dateArr[2].split(" ");
            var day = tmp[0];
            var timeArr = tmp[1].split(":");
            var hour = timeArr[0];
            var min = timeArr[1];

            return [year, month, day, hour, min];

        }


    }]);