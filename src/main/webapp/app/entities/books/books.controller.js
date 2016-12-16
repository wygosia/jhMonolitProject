(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('BooksController', BooksController);

    BooksController.$inject = ['$scope', '$state', 'Books'];

    function BooksController ($scope, $state, Books) {
        var vm = this;

        vm.books = [];

        loadAll();

        function loadAll() {
            Books.query(function(result) {
                vm.books = result;
            });
        }
    }
})();
