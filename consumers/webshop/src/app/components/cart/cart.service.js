(function() {
  'use strict';

  angular
    .module('webapp')
    .service('CartService', CartService);

  /** @ngInject */
  function CartService($http, $q) {
    this.query = function() {
      var deferred = $q.defer();

      $http.get('/api/cart')
        .success(deferred.resolve)
        .error(function(error) {
          console.error('error calling cart service', error);
          deferred.reject(error);
        });

      return deferred.promise;
    };
  }
})();
