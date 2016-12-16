(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('Toys', Toys);

    Toys.$inject = ['$resource'];

    function Toys ($resource) {
        var resourceUrl =  'api/toys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
