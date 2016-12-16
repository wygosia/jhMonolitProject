'use strict';

describe('Controller Tests', function() {

    describe('Jhi_user Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJhi_user;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJhi_user = jasmine.createSpy('MockJhi_user');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Jhi_user': MockJhi_user
            };
            createController = function() {
                $injector.get('$controller')("Jhi_userDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'myappApp:jhi_userUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
