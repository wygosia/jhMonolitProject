(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('BooksDialogController', BooksDialogController);

    BooksDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Books', 'User'];

    function BooksDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Books, User) {
        var vm = this;

        vm.books = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.books.id !== null) {
                Books.update(vm.books, onSaveSuccess, onSaveError);
            } else {
                Books.save(vm.books, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:booksUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
