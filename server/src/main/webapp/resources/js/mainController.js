/// <reference path="angular.d.ts"/>
'use strict';
var FormattedTag = (function () {
    function FormattedTag(name, size) {
        this.weight = size;
        this.text = name;
        this.link = "/tag/articles/" + name;
    }
    return FormattedTag;
}());
var MainController = (function () {
    function MainController($scope, $http) {
        this.postsCount = 0;
        this.loadedPost = 5;
        this.http = $http;
        this.scope = $scope;
        this.init();
    }
    MainController.prototype.init = function () {
        var _this = this;
        this.scope.posts = new Array();
        this.http.get("article/count").success(function (data, status) { return _this.allPostCount = data; });
        this.http.get("article/getlast/" + this.postsCount + "/" + (this.postsCount + this.loadedPost)).success(function (data, status) { _this.posts = data; _this.addPosts(); });
        this.http.get("/article/gettags/all").success(function (data, status) { _this.scope.tags = data; _this.formatTags(); });
    };
    MainController.prototype.loadMore = function () {
        var _this = this;
        if (this.allPostCount > this.postsCount) {
            this.http.get("article/getlast/" + this.postsCount + "/" + (this.postsCount + this.loadedPost)).success(function (data, status) {
                _this.posts = data;
                _this.addPosts();
            });
        }
    };
    ;
    MainController.prototype.addPosts = function () {
        for (var i = 0; i < this.posts.length; i++) {
            this.scope.posts[this.postsCount + i] = JSON.parse('' + JSON.stringify(this.posts[i]));
        }
        this.postsCount += this.loadedPost;
    };
    MainController.prototype.formatTags = function () {
        this.words = new Array();
        for (var i = 0; i < this.scope.tags.length; i++) {
            this.words[i] = new FormattedTag(this.scope.tags[i].name, this.scope.tags[i].articles.length);
        }
    };
    return MainController;
}());
var app = angular
    .module("app", ['angular-jqcloud', 'scroll'])
    .controller("MainController", ["$scope", "$http", MainController]);
angular.module('scroll', []).directive('whenScrolled', function () {
    return function ($scope, elm, attr) {
        var raw = elm[0];
        elm.bind('scroll', function () {
            if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                $scope.$apply(attr.whenScrolled);
            }
        });
    };
});
//# sourceMappingURL=mainController.js.map