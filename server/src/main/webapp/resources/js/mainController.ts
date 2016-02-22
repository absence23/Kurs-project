/// <reference path="angular.d.ts"/>

'use strict';

class FormattedTag{
    constructor(name: string, size: number){
        this.weight = size;
        this.text = name;
        this.link = "/tag/articles/" + name;
    }

    text: string;
    weight: number;
    link: string;
}

class MainController{

    constructor( $scope: ng.IScope,
                $http: ng.IHttpService){
        this.http = $http;
        this.scope = $scope;
        this.init();
    }

    http: ng.IHttpService;
    scope: ng.IScope;
    words: FormattedTag[];
    postsCount: number = 0;
    loadedPost: number = 5;
    posts: any;
    allPostCount: number;

    init(){
        this.scope.posts = new Array();
        this.http.get("article/count").success((data, status) => this.allPostCount = data);
        this.http.get("article/getlast/" + this.postsCount + "/" + (this.postsCount + this.loadedPost)).success((data, status) => {this.posts = data; this.addPosts()});
        this.http.get("/article/gettags/all").success((data, status) => {this.scope.tags = data; this.formatTags()});
    }

    loadMore() {
        if (this.allPostCount > this.postsCount) {
        this.http.get("article/getlast/" + this.postsCount + "/" + (this.postsCount + this.loadedPost)).success((data, status) => {
            this.posts = data;
            this.addPosts()
        });
        }
    };

    addPosts(){
        for (var i = 0; i < this.posts.length; i++){
            this.scope.posts[this.postsCount + i] = JSON.parse('' + JSON.stringify(this.posts[i]));
        }
        this.postsCount += this.loadedPost;
    }

    formatTags(){
        this.words = new Array();
        for(var i = 0; i < this.scope.tags.length; i++){
            this.words[i] = new FormattedTag(this.scope.tags[i].name, this.scope.tags[i].articles.length);
        }
    }
}


var app = angular
    .module("app", ['angular-jqcloud', 'scroll'])
    .controller("MainController", ["$scope", "$http", MainController]);


angular.module('scroll', []).directive('whenScrolled', () => {
    return function($scope, elm, attr) {
        var raw = elm[0];
        elm.bind('scroll', function() {
            if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight)
            {
                $scope.$apply(attr.whenScrolled);
            }
        });
    };
});


