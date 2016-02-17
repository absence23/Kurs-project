/// <reference path="angular.d.ts"/>

'use strict';

class ProfileController{
    constructor(){
        this.isEditing = false;
    }

    isEditing: boolean;

    edit(){
        this.isEditing = true;
    }

    save(){
        this.isEditing = false;
    }

    reset(){
        this.isEditing = false;
    }
}


var app = angular
    .module("app", [])
    .controller("ProfileController", ProfileController);

