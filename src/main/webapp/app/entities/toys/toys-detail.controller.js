(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('ToysDetailController', ToysDetailController);

    ToysDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Toys'];

    function ToysDetailController($scope, $rootScope, $stateParams, previousState, entity, Toys) {
        var vm = this;

        vm.toys = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:toysUpdate', function(event, result) {
            vm.toys = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
