(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('ToysDeleteController',ToysDeleteController);

    ToysDeleteController.$inject = ['$uibModalInstance', 'entity', 'Toys'];

    function ToysDeleteController($uibModalInstance, entity, Toys) {
        var vm = this;

        vm.toys = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Toys.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
