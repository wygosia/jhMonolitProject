(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('ToysController', ToysController);

    ToysController.$inject = ['$scope', '$state', 'Toys'];

    function ToysController ($scope, $state, Toys) {
        var vm = this;

        vm.toys = [];

        loadAll();

        function loadAll() {
            Toys.query(function(result) {
                vm.toys = result;
            });
        }
    }
})();
