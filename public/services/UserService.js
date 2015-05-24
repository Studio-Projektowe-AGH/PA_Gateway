/**
 * Created by Dominika on 2015-04-17.
 */
/*
 *UserService is designed to interact with RESTful web service to manage users
 * within the system
 */
angular.module('UserModule', [])
    .factory('UserService', ['$http', function ($http) {
        var service = {};

        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.GetBusinessProfile = GetBusinessProfile;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
        service.SignUp = SignUp;
        service.SignIn = SignIn;
        service.SignInFacebook = SignInFacebook;
        service.SignOut = SignOut;

        return service;

        function SignUp(userData, successCallback, errorCallback) {
            var config = {
                method: 'POST',
                url: "/auth/signup",
                data: angular.toJson(userData),
                headers: {
                    'Content-Type': 'application/json',
                    'Accept' : 'application/json'
                }
            };
            return $http(config).then(successCallback, errorCallback);
        }


        function SignIn(userData, handleSuccess, handleError) {
            var config = {
                method: 'POST',
                url: "/auth/signin/credentials",   //"/auth/signin",
                data: angular.toJson(userData),
                headers: {
                    'Content-Type': 'application/json',
                    'Accept' : 'application/json'
                }
            };
            return $http(config).then(handleSuccess, handleError);
        }

        function SignInFacebook(userData, handleSuccess){
            userData.providerName = "facebook";
            var config = {
                method: 'POST',
                url: "/auth/signin/facebook",
                data: angular.toJson(userData),
                headers: {
                    'Content-Type': 'application/json',
                    'Accept' : 'application/json'
                }
            };
            return $http(config).then(handleSuccess, handleError);
        }

        function SignOut(email, successCallback) {
            return $http.get("/auth/signout/" + email).then(successCallback, handleError('Error in signing out"'));

        }

        function GetById(id, successCallback) {
            return $http.get("../business_client/test/person.json").then(successCallback, handleError('Error getting user by id'));

            // return $http.get('/api/do/username' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetBusinessProfile(successCallback) {
            alert("funkcja");
            return $http.get('/profiles/business').then(successCallback, handleError('Error getting business profile'));
        }


        function GetByUsername(username, successCallback) {
            return $http.get('/user/' + username).then(successCallback, handleError('Error getting user by username'));
        }

        //function GetByUsername(username, successCallback) {
        //    return $http.get('https://profile-service.herokuapp.com/userProfile/5554f84952423afe1e6ebdcf' + username).then(successCallback, handleError('Error getting user by username'));
        //}

        function Create(user) {
            return $http.post('/api/users', user).then(handleSuccess, handleError('Error creating user'));
        }

        function Update(user) {
            return $http.put('/api/users/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(user) {
            return $http.delete('/api/users/' + user.id).then(handleSuccess, handleError('Error deleting user'));
        }

        function handleSuccess(dataFromServer, status, headers, config) {
            alert("usalo sie!");
            return dataFromServer;
        }

        function handleError(dataFromServer, status, headers, config) {
            //alert("blad!!! " + status);
            return {success: false, message: status};
        }
    }]);