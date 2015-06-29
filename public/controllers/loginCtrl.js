angular.module('LoginModule', ['facebook','AuthenticationModule', 'FacebookModule', 'UserModule'])
    .controller('LoginCtrl', ['$scope', '$rootScope', '$location', 'Facebook','AuthenticationService', 'FacebookService', 'UserService',
        function ($scope, $rootScope, $location, Facebook, AuthenticationService, FacebookService, UserService) {
            $scope.info = "";
            $scope.login = login;
            $scope.loginWithFB = loginWithFB();

            (function initController() {
                //reset login status
                AuthenticationService.ClearCredentials();
            })();

            function login() {
                $scope.dataLoading = true;
                var user = {
                    email: $scope.user.email,
                    password: $scope.user.password
                };

                UserService.SignIn(user, function (response) {
                    AuthenticationService.SetCredentials(response.data.access_token, user.email);
                    $rootScope.$apply(function(){
                        $rootScope.isLogged = true;
                    });
                    $location.path('/home');
                    $scope.dataLoading = false;
                }, function (response) {
                    if (response.status == 401) {
                        $scope.info = "Błędny email lub hasło, popraw!";
                    } else {
                        $scope.info = "Wystąpił niespodziewany błąd spróbuj jeszcze raz.";
                    }

                    $scope.dataLoading = false;
                });
            }

            function loginWithFB() {
               // FacebookService.checkFBLoginState();
               //userData.email = $rootScope.globals.currentUser.email;
               // UserService.SignInFacebook(userData, function (response) {
               //     AuthenticationService.SetCredentials(response.data.access_token, userData.email);
               //     $location.path('/home');
               //     $scope.dataLoading = false;
               // })
                $scope.dataLoading = true;
                Facebook.login(function(response){
                    if(response.status == 'connected'){
                       var access_token = FB.getAuthResponse()['accessToken'];
                        console.log('Access Token: ' + access_token);
                        var data = {
                                    accessToken: access_token,
                                    providerName: "facebook"
                                };
                        Facebook.api('/me',  function(response) {
                        //    var data = {
                        //        accessToken: response.authResponse.access_token,
                        //        providerName: "facebook"
                        //    };
                            UserService.SignInFacebook(data, function (response2) {
                                AuthenticationService.SetCredentials(response2.data.access_token, response.email);
                                $rootScope.$apply(function(){
                                    $rootScope.isLogged = true;
                                });
                                $location.path('/home');
                                $scope.dataLoading = false;
                            })
                        })
                    } else if (response.status === 'not_authorized') {
                        // The person is logged into Facebook, but not your app.
                        document.getElementById('status').innerHTML = 'Please log ' +
                        'into this app.';
                    } else {
                        // The person is not logged into Facebook, so we're not sure if
                        // they are logged into this app or not.
                        document.getElementById('status').innerHTML = 'Please log ' +
                        'into Facebook.';
                    }

                })


            }

        }]);