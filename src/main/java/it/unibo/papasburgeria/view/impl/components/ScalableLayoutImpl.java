package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.api.components.ScaleConstraint;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of LayoutManager2 for a scale-based layout manager.
 *
 * <p>
 * See {@link LayoutManager2} for interface details.
 */
public class ScalableLayoutImpl implements LayoutManager2 {
    private final Map<Component, ScaleConstraint> mappedConstraints;

    /**
     * Constructs a ScalableLayout.
     */
    public ScalableLayoutImpl() {
        mappedConstraints = new HashMap<>();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addLayoutComponent(final Component component, final Object constraint) {
        if (component == null || !(constraint instanceof ScaleConstraint)) {
            throw new IllegalArgumentException("You must provide a component with a ScaleConstraint for this layout");
        }

        this.mappedConstraints.put(component, (ScaleConstraint) constraint);
    }

    /**
     * @inheritDoc Note: Not supported.
     */
    @Override
    public void addLayoutComponent(final String name, final Component comp) {
        throw new UnsupportedOperationException("This layout does not support addLayoutComponent(String, Component)");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeLayoutComponent(final Component component) {
        if (component == null) {
            throw new IllegalArgumentException("You must provide a valid component");
        }

        this.mappedConstraints.remove(component);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Dimension minimumLayoutSize(final Container target) {
        if (target == null) {
            throw new IllegalArgumentException("You must provide a valid container");
        }

        return target.getSize();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Dimension maximumLayoutSize(final Container target) {
        if (target == null) {
            throw new IllegalArgumentException("You must provide a valid container to run maximumLayoutSize");
        }

        return target.getSize();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Dimension preferredLayoutSize(final Container target) {
        if (target == null) {
            throw new IllegalArgumentException("You must provide a valid container to run preferredLayoutSize");
        }

        return target.getSize();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void layoutContainer(final Container target) {
        if (target == null) {
            throw new IllegalArgumentException("You must provide a valid container to run layoutContainer");
        }

        final Dimension size = target.getSize();
        mappedConstraints.forEach((component, constraint) -> {
            final Scale sizeScale = constraint.getSizeScale();
            final Scale positionScale = constraint.getPositionScale();
            final Scale originScale = constraint.getOriginScale();
            final int width = sizeScale.getXScaledValue(size.width);
            final int height = sizeScale.getYScaledValue(size.height);
            component.setBounds(
                    positionScale.getXScaledValue(size.width) - originScale.getXScaledValue(width),
                    positionScale.getYScaledValue(size.height) - originScale.getYScaledValue(height),
                    width,
                    height
            );
        });
    }

    /**
     * @inheritDoc Note: Defaults to 0.0f.
     */
    @Override
    public float getLayoutAlignmentX(final Container target) {
        return 0.0f;
    }

    /**
     * @inheritDoc Note: Defaults to 0.0f.
     */
    @Override
    public float getLayoutAlignmentY(final Container target) {
        return 0.0f;
    }

    /**
     * @inheritDoc Note: No implementation as there's no internal cache.
     */
    @Override
    public void invalidateLayout(final Container target) {
    }
}
