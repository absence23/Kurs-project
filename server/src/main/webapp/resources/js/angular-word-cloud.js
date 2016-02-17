/*! angular-word-cloud 2013-11-18 */
angular.module("vr.directives.wordCloud",[]).directive("wordCloud",["$interpolate",function(a){return{restrict:"EA",replace:!0,transclude:!0,scope:{words:"=",sort:"@"},template:"<div class='word-cloud-group'><span class='word-cloud-group-item' ng-repeat='word in mywords | orderBy:param:reverse' ng-transclude></span></div>",controller:["$scope","$transclude",function(a,b){a.initClick=function(c){b(function(b,d){a.clickFn=d[c]})}}],compile:function(b,c){var d=angular.isUndefined(c.type)?"list":c.type;switch(d){case"cloud":b.children().eq(0).attr("style","font-size: "+a.startSymbol()+" fontSize(word.size) "+a.endSymbol()+";");break;case"list":}return function(a,b,c){a.clickFn=function(){},angular.isUndefined(c.ngClick)||a.initClick(c.ngClick);var d=function(){var b=angular.copy(a.words);angular.isArray(b)&&b.length>0?angular.isObject(b[0])?(angular.isUndefined(b[0].word)||angular.isUndefined(b[0].size))&&(b=[]):b=b.map(function(a){return{word:a,size:1}}):b=[],b=b.map(function(a){return{word:a.word,size:a.size,rawSize:parseFloat(a.size)}}),a.mywords=b};a.fontSize=function(a){return-1==(""+a).search("(px|em|in|cm|mm|ex|pt|pc|%)+")?a+"em":a},a.$watch("words",function(){d()},!0),a.$watch("sort",function(b){b||(b="no"),a.param="alpha"==b.substr(0,5)?"word":"no"==b?"":"rawSize",a.reverse="desc"==b.substr(-4).toLowerCase()})}}}}]);