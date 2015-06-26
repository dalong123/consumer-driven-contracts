(function() {
  'use strict';

  angular
    .module('frontend')
    .factory('Report', Report);

  ///** @ngInject */
  //function Report($resource) {
  //  return $resource('http://localhost:1234/public/report', {});
  //}

  function Report($http, $log) {
    var apiHost = 'http://localhost:1234';

    var service = {
      query: query
    };

    return service;

    function query(callback) {
      var url = apiHost + '/api/public/report';
      $log.info('IN QUERY', url);
      $.ajax({
        url: url,
        headers: {
          'Accept': 'application/json'
        },
        success: function(data) {
          $log.debug(data);
          callback(data);
        },
        error: function(xhr, textStatus, errorThrown) {
          $log.error(textStatus, errorThrown);
          callback();
        }
      });
      //$http.get(url).success(function(response) {
      //  console.log('Response:', response);
      //  callback(response.data);
      //}).error(function(error) {
      //  console.error('get report failed', error);
      //  callback({'error': error});
      //});
    }
  }
})();

