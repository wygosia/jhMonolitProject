(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('BooksDetailController', BooksDetailController);

    BooksDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Books', 'User'];

    function BooksDetailController($scope, $rootScope, $stateParams, previousState, entity, Books, User) {
        var vm = this;

        vm.books = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:booksUpdate', function(event, result) {
            vm.books = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
