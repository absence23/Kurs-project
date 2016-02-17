/// <reference path="angular.d.ts"/>

'use strict';
class ArticleController{
    constructor($http: ng.IHttpService){
        this.isEditing = false;
        this.http = $http;
    this.tags = [
            { id: 1, name: 'Tag1' },
            { id: 2, name: 'Tag2' },
            { id: 3, name: 'Tag3' }
        ];
    }

    tags: any;
    isEditing: boolean;
    text: any;
    http: ng.IHttpService;
    id: number;


    articleInit(id: number){
        this.id = id;
        this.getTags();
        this.http.get("/article/get/" + id).success((data, status) => this.text = data.article);
    }

    getTags(){
        this.http.get("/article/gettags/" + this.id).success((data, status) => {this.tags = data; console.log(data)})
    }

    saveTags(){
        console.log("save");
        this.http.post("/article/settags/" + this.id, this.tags).then((data) => console.log(data), (data) => console.log(data));
    }

    edit(){
        this.isEditing = true;
    }

    save(){
        this.saveTags();
        this.isEditing = false;
    }
}

var app = angular
    .module("app", ['markdown', 'ngSanitize', 'ngRoute', 'ngTagsInput'])
    .controller("ArticleController",  ["$http", ArticleController]);

