(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('toys', {
            parent: 'entity',
            url: '/toys',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Toys'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/toys/toys.html',
                    controller: 'ToysController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('toys-detail', {
            parent: 'entity',
            url: '/toys/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Toys'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/toys/toys-detail.html',
                    controller: 'ToysDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Toys', function($stateParams, Toys) {
                    return Toys.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'toys',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('toys-detail.edit', {
            parent: 'toys-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/toys/toys-dialog.html',
                    controller: 'ToysDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Toys', function(Toys) {
                            return Toys.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('toys.new', {
            parent: 'toys',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/toys/toys-dialog.html',
                    controller: 'ToysDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                category: null,
                                avg_price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('toys', null, { reload: 'toys' });
                }, function() {
                    $state.go('toys');
                });
            }]
        })
        .state('toys.edit', {
            parent: 'toys',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/toys/toys-dialog.html',
                    controller: 'ToysDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Toys', function(Toys) {
                            return Toys.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('toys', null, { reload: 'toys' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('toys.delete', {
            parent: 'toys',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/toys/toys-delete-dialog.html',
                    controller: 'ToysDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Toys', function(Toys) {
                            return Toys.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('toys', null, { reload: 'toys' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
