/// <reference path="angular.d.ts"/>
'use strict';
var MainController = (function () {
    function MainController($scope, $http) {
        this.http = $http;
        this.scope = $scope;
        this.init();
    }
    MainController.prototype.init = function () {
        var _this = this;
        this.http.get("article/getlast").success(function (data, status) { _this.scope.posts = data; console.log(data); });
    };
    return MainController;
}());
var app = angular
    .module("app", [])
    .controller("MainController", ["$scope", "$http", MainController]);
//# sourceMappingURL=mainController.js.map