angular.module('myApp',
    ['ngRoute', 'ngCookies', 'facebook', 'AuthenticationModule', 'UserModule', 'RegisterModule', 'HomeModule', 'LoginModule'])
    .config(['FacebookProvider', function(FacebookProvider){
        FacebookProvider.init('504846879664518');
    }])
    .run(['$rootScope', '$location', '$cookieStore', '$http', '$window',
        function ($rootScope, $location, $cookieStore, $http, $window) {
        //facebook stuff, load SDK
        //    $window.fbAsyncInit = function(){
        //        FB.init({
        //            appId : '504846879664518',
        //            status : true,
        //            cookie : true,
        //            xfbml : true,
        //            version    : 'v2.2'
        //        });
        //
        //    };
        //    (function(d,s,id){
        //        var js, fjs = d.getElementsByTagName(s)[0];
        //        if (d.getElementById(id)) {
        //            return;
        //        }
        //        js = d.createElement(s);
        //        js.id = id;
        //        js.async = true;
        //        js.src = "//connect.facebook.net/en_US/sdk.js";
        //        fjs.parentNode.insertBefore(js, fjs);
        //    }(document, 'script', 'facebook-jssdk'));

        //keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + $rootScope.globals.currentUser.access_token;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            //redirect to login page if not logged in and trying to access a restricted page
            var loggedIn = $rootScope.globals.currentUser;

            if (($location.path() != ('/login' || '/register') ) && !loggedIn) {
                $rootScope.mainPath = '/register';
                $location.path('/register');
            }
            else if (loggedIn) {
                $rootScope.mainPath = '/home';
                if($location.path() == ('/login' || '/register')){
                    $location.path('/home');
                }

            }
        });
    }])
    .config(['$routeProvider','$httpProvider',
        function ($routeProvider, $httpProvider) {
            $httpProvider.defaults.headers.post['Content-Type'] = 'application/json';
            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            delete $httpProvider.defaults.headers.common['Access-Control-Request-Method'];
            delete $httpProvider.defaults.headers.common['Access-Control-Request-Headers'];

        $routeProvider
            .when('/home', {
                controller: 'HomeCtrl',
                templateUrl: 'views/home.html'
            })
            .when('/login', {
                controller: 'LoginCtrl',
                templateUrl: 'views/loginView.html',
                public : true,
                login : true
            })
            .when('/register', {
                controller: 'RegisterCtrl',
                templateUrl: 'views/registerView.html',
                public : true
            })
            .when('/messages', {
                controller: 'HomeCtrl',
                templateUrl: 'views/home.html'
            })
            .otherwise({
                redirectTo: '/home'
            })
    }]);
