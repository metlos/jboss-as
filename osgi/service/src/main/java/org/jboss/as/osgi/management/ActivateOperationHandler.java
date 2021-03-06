/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.osgi.management;

import org.jboss.as.controller.AbstractRuntimeOnlyHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationContext.Stage;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.osgi.service.FrameworkActivator;
import org.jboss.dmr.ModelNode;

/**
 * Operation to activate the OSGi subsystem.
 *
 * @author David Bosschaert
 * @author Thomas.Diesler@jboss.com
 */
public class ActivateOperationHandler extends AbstractRuntimeOnlyHandler  {

    public static ActivateOperationHandler INSTANCE = new ActivateOperationHandler();

    private ActivateOperationHandler() {
    }

    @Override
    protected void executeRuntimeStep(OperationContext context, ModelNode operation) throws OperationFailedException {

        // This verification handler will cause context.completeStep() to wait until controller is active.
        ServiceVerificationHandler verificationHandler = new ServiceVerificationHandler();
        if (FrameworkActivator.activateEagerly(verificationHandler)) {
            context.addStep(verificationHandler, Stage.VERIFY);
        }

        context.completeStep(OperationContext.RollbackHandler.NOOP_ROLLBACK_HANDLER);
    }
}
