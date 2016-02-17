/// <reference path="angular.d.ts"/>
'use strict';
var ProfileController = (function () {
    function ProfileController() {
        this.isEditing = false;
    }
    ProfileController.prototype.edit = function () {
        this.isEditing = true;
    };
    ProfileController.prototype.save = function () {
        this.isEditing = false;
    };
    ProfileController.prototype.reset = function () {
        this.isEditing = false;
    };
    return ProfileController;
}());
var app = angular
    .module("app", [])
    .controller("ProfileController", ProfileController);
//# sourceMappingURL=profileController.js.map