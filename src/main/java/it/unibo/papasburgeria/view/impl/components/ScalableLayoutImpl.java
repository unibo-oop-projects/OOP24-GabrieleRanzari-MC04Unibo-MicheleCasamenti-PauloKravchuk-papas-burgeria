package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.api.components.ScaleConstraint;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
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

        this.mappedConstraints.put(component, new ScaleConstraintImpl((ScaleConstraint) constraint));
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

            final Icon icon = getComponentIcon(component);
            if (icon instanceof ImageIcon) {
                setComponentIcon(
                        component,
                        new ImageIcon(((ImageIcon) icon)
                                .getImage()
                                .getScaledInstance(width, height, Image.SCALE_SMOOTH)));
            }
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

    /**
     * Method to obtain the Icon reference from a JComponent, implementation is
     * on a per-need basis as Swing does not have one main class to handle getIcon methods
     * across components.
     *
     * @param component component having the icon
     * @return the Icon instance
     */
    private Icon getComponentIcon(final Component component) {
        return switch (component) {
            case AbstractButton btn -> btn.getIcon();
            case JLabel lbl -> lbl.getIcon();
            default -> null;
        };
    }

    /**
     * Sets component's icon if possible, otherwise throws an error.
     *
     * @param component component to set the icon to
     * @param icon      the icon instance
     */
    private void setComponentIcon(final Component component, final Icon icon) {
        switch (component) {
            case AbstractButton btn -> btn.setIcon(icon);
            case JLabel lbl -> lbl.setIcon(icon);
            default ->
                    throw new IllegalStateException("Obtained component's icon but have no way to set it for: " + component);
        }
    }
}
