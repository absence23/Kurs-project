/// <reference path="angular.d.ts"/>

'use strict';

class MainController{

    constructor( $scope: ng.IScope,
                $http: ng.IHttpService){
        this.http = $http;
        this.scope = $scope;
        this.init();
    }

    http: ng.IHttpService;
    scope: ng.IScope;

    init(){
        this.http.get("article/getlast").success((data, status) => {this.scope.posts = data; console.log(data)});
    }
}


var app = angular
    .module("app", [])
    .controller("MainController", ["$scope", "$http", MainController]);

