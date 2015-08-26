(function() {
  'use strict';

  angular
    .module('webapp')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($timeout, webDevTec, CartService) {
    var vm = this;

    vm.awesomeThings = [];
    vm.cartItems = [];
    vm.creationDate = 1440511594348;

    activate();

    function activate() {
      getWebDevTec();
      getCart();
    }

    function getWebDevTec() {
      vm.awesomeThings = webDevTec.getTec();

      angular.forEach(vm.awesomeThings, function(awesomeThing) {
        awesomeThing.rank = Math.random();
      });
    }

    function getCart() {
      CartService.query().then(function(response) {
        vm.cartItems = response.items;
      });
    }
  }
})();
