/**
 * Created by Dominika on 2015-06-28.
 */
angular.module('StatsModule', ['angularCharts', 'StatsModule', 'GeneratorModule'])
    .controller('StatsCtrl', ['$scope', '$rootScope', 'StatService', 'GeneratotService', function ($scope, $rootScope, stat, generator) {
        $scope.requestData = {
            value: "total visits",
            date_from: "2015-06-01",
            date_to: "2015-06-12 12:00",
            aggregate: "day"
        };
        $scope.aggregate = "day";
        $scope.cos = "43\nIlosc kodow QR: 18";
        $scope.diagType = "";
        $scope.types = ["pie", "line", "bar", "area", "point"];
        $scope.data = {
            series: ['One seires', 'Income'],
            data: [{
                x: "Przykladowe dane",
                y: [12, 122, 1, 2, 1, 31, 2]
            }, {
                x: "Income",
                y: [1, 32, 12, 1, 21, 4, 12, 2],
                tooltip: "jakis tooltip3 + $scope.cos" + $scope.cos
            }]
        };

        $scope.dataBar = {
            series: ['Liczba sprzedanych koktaili', 'Liczba sprzedanych piw'],
            data: [{
                x: "piwo",
                y: [90, 36]
            }, {
                x: "koktaile",
                y: [32, 12]
            }, {
                x: "wódka",
                y: [132, 23],
                tooltip: "sztuk sprzedanych napoji"
            }]
        };
        $scope.config = {
            title: 'Przykladowy wykres', // chart title. If this is false, no title element will be created.
            tooltips: true,
            labels: false, // labels on data points
            // exposed events
            mouseover: function () {
            },
            mouseout: function () {
            },
            click: function () {
            },
            // legend config
            legend: {
                display: true, // can be either 'left' or 'right'.
                position: 'left'

            }
      };

        $scope.getData = function () {
            if($scope.aggregate == "day"){
                stat.getDayStats($scope.requestData, function(response){
                        $scope.localData = response.data;
                    }, function(err){

                    })
            }else{
                stat.getWeekStats($scope.requestData, function(response){
                    $scope.localData = response.data;
                },function(err){

                })
            }

        };

        $scope.changeDiagram = function () {
            $scope.lineDiag = !$scope.lineDiag;
        };
        $scope.pieDiagram = function () {
            $scope.diagType = $scope.types[0];
        };
        $scope.lineDiagram = function () {
            $scope.diagType = $scope.types[1];
        };
        $scope.barDiagram = function () {
            $scope.diagType = $scope.types[2];
        };
        $scope.areaDiagram = function () {
            $scope.diagType = $scope.types[3];
        };
        $scope.pointDiagram = function () {
            $scope.diagType = $scope.types[4];
        };
    }]);