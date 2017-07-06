angular.module('ngBoilerplate.account', ['ui.router', 'ngResource', 'base64'])
.config(function($stateProvider) {
    $stateProvider.state('adduser', {
            url:'/adduser',
            views: {
                'main': {
                    templateUrl:'account/adduser.tpl.html',
                    controller: 'AddUserCtrl'
                }
            },
            data : { pageTitle : "Add and Retrieve User" }
            }
    );

})
.factory('sessionService', function($http, $base64) {
    var session = {};

    return session;
})

.factory('accountService', function($resource) {
    var service = {};
    service.addUser = function(account, success, failure) {
        var Account = $resource("/basic-web-app/rest/accounts");
        Account.save({}, account, success, failure);
    };
    service.getAccountById = function(accountId) {
        var Account = $resource("/basic-web-app/rest/accounts/:paramAccountId");
        return Account.get({paramAccountId:accountId}).$promise;
    };

    service.getAllAccounts = function() {
          var Account = $resource("/basic-web-app/rest/accounts");
          return Account.get().$promise.then(function(data) {
            return data.accounts;
          });
      };
    return service;
})

.controller("AddUserCtrl", function($scope, sessionService, $state, accountService) {
    $scope.addUser = function() {
        accountService.addUser($scope.account,
        function(returnedData) {
             alert("Added successfully");
        },
        function() {
            alert("Error adding user");
        });
    };
     $scope.getAllAccounts = function() {
        accountService.getAllAccounts();
        
    };

})
.controller("AccountSearchCtrl", function($scope, accounts) {
    $scope.accounts = accounts;
})

.controller('RetrieveuserCtrl',['$scope', '$http', function($scope,$http) {

     $scope.clickButton = function(enteredValue){
        //Check http request
       var url = 'http://localhost:8080/basic-web-app/rest/accounts';
        $http.get(url).success(function(data){
        $scope.results = data.accounts;
        console.log($scope.results);
             });
    };
}]);

