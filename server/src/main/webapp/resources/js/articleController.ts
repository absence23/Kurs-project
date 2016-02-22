/// <reference path="angular.d.ts"/>

'use strict';

class Tag{
    name: string;
    id: number;

    constructor(id: number, name: string){
        this.id = id;
        this.name = name;
    }
}

class ArticleController{
    constructor($http: ng.IHttpService){
        this.isEditing = false;
        this.http = $http;
    this.tags = new Array();
    }

    tags: Tag[];
    sendedTags: any;
    isEditing: boolean;
    text: any;
    http: ng.IHttpService;
    id: number;


    loadTags(query) {
        return this.http.get("/article/gettags/all").then((data) => {
            this.sendedTags = data.data;
            var tags = new Array();
            this.setTags(tags);
            return tags.filter(function(tag){return tag.name.indexOf(query) != -1;});
        });
    };

    articleInit(id: number){
        console.log("init");
        console.log(id);
        this.id = id;
        this.getTags();
        this.http.get("/article/get/" + id).success((data, status) => this.text = data.article);
    }

    getTags(){
        this.http.get("/article/gettags/" + this.id).success((data, status) => {this.sendedTags = data; this.setTags(this.tags);})
    }

    setTags(tags: any){
        for(var i = 0; i < this.sendedTags.length; i++){
            tags[i] = new Tag(this.sendedTags[i].id, this.sendedTags[i].name);
        }
    }

    saveTags(){
        this.http.post("/article/settags/" + this.id, this.tags).success((data, status) => console.log(status));
        this.http.get("/tag/check/");
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

