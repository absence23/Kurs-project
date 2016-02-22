/// <reference path="angular.d.ts"/>
'use strict';
var Tag = (function () {
    function Tag(id, name) {
        this.id = id;
        this.name = name;
    }
    return Tag;
}());
var ArticleController = (function () {
    function ArticleController($http) {
        this.isEditing = false;
        this.http = $http;
        this.tags = new Array();
    }
    ArticleController.prototype.loadTags = function (query) {
        var _this = this;
        return this.http.get("/article/gettags/all").then(function (data) {
            _this.sendedTags = data.data;
            var tags = new Array();
            _this.setTags(tags);
            return tags.filter(function (tag) { return tag.name.indexOf(query) != -1; });
        });
    };
    ;
    ArticleController.prototype.articleInit = function (id) {
        var _this = this;
        console.log("init");
        console.log(id);
        this.id = id;
        this.getTags();
        this.http.get("/article/get/" + id).success(function (data, status) { return _this.text = data.article; });
    };
    ArticleController.prototype.getTags = function () {
        var _this = this;
        this.http.get("/article/gettags/" + this.id).success(function (data, status) { _this.sendedTags = data; _this.setTags(_this.tags); });
    };
    ArticleController.prototype.setTags = function (tags) {
        for (var i = 0; i < this.sendedTags.length; i++) {
            tags[i] = new Tag(this.sendedTags[i].id, this.sendedTags[i].name);
        }
    };
    ArticleController.prototype.saveTags = function () {
        this.http.post("/article/settags/" + this.id, this.tags).success(function (data, status) { return console.log(status); });
        this.http.get("/tag/check/");
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