/**
 * Created by Dominika on 2015-04-20.
 */
angular.module('FacebookModule', [])
    .factory('FacebookService', function () {
        var getUserInfo = function(){
            FB.api('/me', function(response){
                $rootScope.$apply(function(){
                    //$rootScope.user = response;
                    $rootScope.globals.currentUser = {
                        email : response.email,
                        access_token : response.authResponse.accessToken
                    };
                    //$http.defaults.headers.common['Authorization'] = 'Bearer' + response.authResponse.accessToken;
                    $cookieStore.put('globals', $rootScope.globals);
                })
            });
        };
        function watchAuthenticationStatusChange(){
            //FB.Event.subscribe('auth.authResponseChange', function(response){
            FB.getLoginStatus(function(response){
                console.log('statusChangeCallback');
                console.log(response);
                // The response object is returned with a status field that lets the
                // app know the current login status of the person.
                // Full docs on the response object can be found in the documentation
                // for FB.getLoginStatus().
                if (response.status === 'connected') {
                    // Logged into your app and Facebook.
                    getUserInfo();
                    testAPI();
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
            });
        }

        function testAPI() {
            console.log('Welcome!  Fetching your information.... ');
            FB.api('/me', function(response) {
                console.log('Successful login for: ' + response.name);
                document.getElementById('status').innerHTML =
                    'Thanks for logging in, ' + response.name + '!';
            });
        }

        return {
            checkFBLoginState : watchAuthenticationStatusChange
        }
})
;