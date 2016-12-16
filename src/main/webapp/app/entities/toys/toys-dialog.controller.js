(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('ToysDialogController', ToysDialogController);

    ToysDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Toys'];

    function ToysDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Toys) {
        var vm = this;

        vm.toys = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.toys.id !== null) {
                Toys.update(vm.toys, onSaveSuccess, onSaveError);
            } else {
                Toys.save(vm.toys, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:toysUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
