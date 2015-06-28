/**
 * Created by Dominika on 2015-06-28.
 */
angular.module('StatsModule', [])
.factory('StatsService', ['$http', '$rootScope', function($http, $rootScope){
    //     /business_stats/values
    //    /business_stats/ratio
        var service = {};
        var request = {
          value : "total visits",
            date_from : "2015-06-01",
            date_to : "2015-06-12 12:00",
            aggregate : "day"
        };
        service.getDayStats = getDayStats;
        service.getWeekStats = getWeekStats;

        function getDayStats(data, successCallback, errorCallback){
            request.value = data.value;
            request.date_from = data.date_from;
            request.date_to = data.date_to;
            request.aggregate = data.aggregate;

            var config = {
                method : 'POST',
                url : "/business_stats/values",
                data : angular.toJson(request),
                headers:{
                    'Content-Type' : 'application/json',
                    'Accept' : 'application/json'
                }
            };
            return $http(config).then(successCallback, errorCallback);
        }

        function getWeekStats(data, successCallback, errorCallback){
            request.value = data.value;
            request.date_from = data.date_from;
            request.date_to = data.date_to;
            request.aggregate = data.aggregate;

            var config = {
                method : 'POST',
                url : "/business_stats/ratio",
                data : angular.toJson(request),
                headers:{
                    'Content-Type' : 'application/json',
                    'Accept' : 'application/json'
                }
            };
            return $http(config).then(successCallback, errorCallback);
        }
    }]);