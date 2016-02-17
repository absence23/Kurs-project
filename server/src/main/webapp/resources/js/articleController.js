/// <reference path="angular.d.ts"/>
'use strict';
var ArticleController = (function () {
    function ArticleController($http) {
        this.isEditing = false;
        this.http = $http;
        this.tags = [
            { id: 1, name: 'Tag1' },
            { id: 2, name: 'Tag2' },
            { id: 3, name: 'Tag3' }
        ];
    }
    ArticleController.prototype.articleInit = function (id) {
        var _this = this;
        this.id = id;
        this.getTags();
        this.http.get("/article/get/" + id).success(function (data, status) { return _this.text = data.article; });
    };
    ArticleController.prototype.getTags = function () {
        var _this = this;
        this.http.get("/article/gettags/" + this.id).success(function (data, status) { _this.tags = data; console.log(data); });
    };
    ArticleController.prototype.saveTags = function () {
        console.log("save");
        this.http.post("/article/settags/" + this.id, this.tags).then(function (data) { return console.log(data); }, function (data) { return console.log(data); });
    };
    ArticleController.prototype.edit = function () {
        this.isEditing = true;
    };
    ArticleController.prototype.save = function () {
        this.saveTags();
        this.isEditing = false;
    };
    return ArticleController;
}());
var app = angular
    .module("app", ['markdown', 'ngSanitize', 'ngRoute', 'ngTagsInput'])
    .controller("ArticleController", ["$http", ArticleController]);
//# sourceMappingURL=articleController.js.map